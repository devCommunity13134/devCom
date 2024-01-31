package devcom.main.domain.reply.controller;

import devcom.main.domain.answer.Service.AnswerService;
import devcom.main.domain.answer.entity.Answer;
import devcom.main.domain.article.entity.Article;
import devcom.main.domain.article.service.ArticleService;
import devcom.main.domain.reply.ReplyForm;
import devcom.main.domain.reply.service.ReplyService;
import devcom.main.domain.user.entity.SiteUser;
import devcom.main.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reply")
public class ReplyController {
    private final ReplyService replyService;
    private final UserService userService;
    private final ArticleService articleService;
    private final AnswerService answerService;

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
}
