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

        return String.format("redirect:/article/detail/%s",articleId);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String answerVote(@PathVariable("id") Long answerId, Principal principal){
        Answer answer = this.answerService.getAnswer(answerId);
        SiteUser siteUser = this.userService.findByUsername(principal.getName());

        this.answerService.voteAnswer(answer, siteUser);
        return String.format("redirect:/article/detail/%s",answer.getOriginalArticle().getId());
    }

}
