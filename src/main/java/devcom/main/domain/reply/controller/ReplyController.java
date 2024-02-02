package devcom.main.domain.reply.controller;

import devcom.main.domain.answer.AnswerForm;
import devcom.main.domain.answer.Service.AnswerService;
import devcom.main.domain.answer.entity.Answer;
import devcom.main.domain.article.entity.Article;
import devcom.main.domain.article.service.ArticleService;
import devcom.main.domain.reply.ReplyForm;
import devcom.main.domain.reply.entity.Reply;
import devcom.main.domain.reply.service.ReplyService;
import devcom.main.domain.user.entity.SiteUser;
import devcom.main.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reply")
public class ReplyController {
    private final ReplyService replyService;
    private final UserService userService;
    private final ArticleService articleService;
    private final AnswerService answerService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String replyCreate(@Valid ReplyForm replyForm, BindingResult bindingResult, Principal principal,
                              @RequestParam("originalArticleId") Long originalArticleId, @RequestParam("originalAnswerId")
                                  Long originalAnswerId){
        if(bindingResult.hasErrors()){
            return String.format("redirect:/article/detail/%s", originalArticleId);
        }
        Article originalArticle = this.articleService.getArticle(originalArticleId);
        Answer origianlAnswer = this.answerService.getAnswer(originalAnswerId);

        SiteUser replyAuthor = this.userService.findByUsername(principal.getName());
        this.replyService.create(replyForm,originalArticle, origianlAnswer, replyAuthor);

        //raise commentSize
        this.articleService.raiseCommentSize(originalArticle);

        return String.format("redirect:/article/detail/%s",originalArticleId);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{replyId}")
    public String deleteReply(@PathVariable("replyId") Long replyId){
        Reply reply = this.replyService.getReply(replyId);
        Article originalArticle = reply.getOriginalArticle();
        this.replyService.delete(reply);

        return String.format("redirect:/article/detail/%s",originalArticle.getId());
    }


    // fixing
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{replyId}")
    public String replyModify(@PathVariable("replyId") Long replyId, @Valid ReplyForm replyForm, BindingResult bindingResult){
        Reply reply = this.replyService.getReply(replyId);
        Article originalArticle = reply.getOriginalArticle();
        if(bindingResult.hasErrors()){
            return String.format("redirect:/article/detail/%s",originalArticle.getId());
        }


        this.replyService.modify(reply,replyForm.getContent());

        return String.format("redirect:/article/detail/%s",originalArticle.getId());
    }
}
