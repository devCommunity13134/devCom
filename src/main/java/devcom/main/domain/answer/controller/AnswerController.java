package devcom.main.domain.answer.controller;

import devcom.main.domain.answer.AnswerForm;
import devcom.main.domain.answer.Service.AnswerService;
import devcom.main.domain.answer.entity.Answer;
import devcom.main.domain.article.entity.Article;
import devcom.main.domain.article.service.ArticleService;
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
@RequestMapping("/answer")
public class AnswerController {
    private final AnswerService answerService;
    private final ArticleService articleService;
    private final UserService userService;

    @PostMapping("/create/{articleId}")
    public String answerCreate(@Valid AnswerForm answerForm , BindingResult bindingResult, @PathVariable("articleId") Long articleId, Principal principal){
        if(bindingResult.hasErrors()){
            return String.format("/article/detail/%s",articleId);
        }
        Article article =this.articleService.getArticle(articleId);
        SiteUser siteUser = this.userService.findByUsername(principal.getName());

        this.answerService.create(article,answerForm.getContent(),siteUser);
        //raise commentSize
        this.articleService.raiseCommentSize(article);

        return String.format("redirect:/article/detail/%s",articleId);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{answerId}")
    public String answerVote(@PathVariable("answerId") Long answerId, Principal principal){
        Answer answer = this.answerService.getAnswer(answerId);
        SiteUser siteUser = this.userService.findByUsername(principal.getName());

        this.answerService.voteAnswer(answer, siteUser);
        return String.format("redirect:/article/detail/%s",answer.getOriginalArticle().getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{answerId}")
    public String answerDelete(@PathVariable("answerId") Long answerId){
        Answer answer = this.answerService.getAnswer(answerId);
        Article originalArticle = answer.getOriginalArticle();
        this.answerService.delete(answer);

        return String.format("redirect:/article/detail/%s",originalArticle.getId());
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{answerId}")
    public String answerModify(@PathVariable("answerId") Long answerId, @Valid AnswerForm answerForm){
        Answer answer = this.answerService.getAnswer(answerId);
        Article originalArticle = answer.getOriginalArticle();
        this.answerService.modify(answer,answerForm.getContent());

        return String.format("redirect:/article/detail/%s",originalArticle.getId());
    }

}
