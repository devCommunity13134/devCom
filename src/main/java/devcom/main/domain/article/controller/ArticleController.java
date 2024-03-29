package devcom.main.domain.article.controller;


import devcom.main.domain.answer.AnswerForm;
import devcom.main.domain.answer.Service.AnswerService;
import devcom.main.domain.answer.entity.Answer;
import devcom.main.domain.article.ArticleForm;
import devcom.main.domain.article.entity.Article;
import devcom.main.domain.article.repository.ArticleRepository;
import devcom.main.domain.article.service.ArticleService;
import devcom.main.domain.category.entity.Category;
import devcom.main.domain.category.service.CategoryService;
import devcom.main.domain.reply.entity.Reply;
import devcom.main.domain.reply.service.ReplyService;
import devcom.main.domain.user.entity.SiteUser;
import devcom.main.domain.user.service.UserService;
import devcom.main.global.rq.Rq;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;
    private final AnswerService answerService;
    private final ReplyService replyService;
    private final CategoryService categoryService;
    private final UserService userService;

    // article list
    @GetMapping("/{category}")
    public String articleList(Model model, @PathVariable("category") String category, @RequestParam(value = "page", defaultValue = "0") int page
            , @RequestParam(value = "keyword", defaultValue = "") String keyword, @RequestParam(value = "sortBy", defaultValue = "createDate") String sortBy) {
        Category category1 = this.categoryService.getCategory(category);
        Page<Article> paging = this.articleService.getArticleList(page, keyword, category1, sortBy);

        model.addAttribute("sortBy", sortBy);
        model.addAttribute("paging", paging);
        model.addAttribute("categoryName", category1.getCategoryName());
        model.addAttribute("keyword", keyword);
        return "article/list";
    }



    // navbar searchList
    @GetMapping("/list")
    public String articleSearchList(Model model,  @RequestParam(value = "page", defaultValue = "0") int page
            , @RequestParam(value = "keywordN", defaultValue = "") String keyword){

        Page<Article> paging = this.articleService.getArticleSearchList(page, keyword);
        model.addAttribute("paging", paging);
        model.addAttribute("keywordN", keyword);
        return "article/searchList";
    }


    // create article
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String articleCreate(ArticleForm articleForm) {

        return "article/form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String articleCreate(@Valid ArticleForm articleForm, BindingResult bindingResult, Principal principal, @RequestParam("thumbnail") MultipartFile thumbnail) {
        if (bindingResult.hasErrors()) {
            return "article/form";
        }

        Category category = this.categoryService.getCategory(articleForm.getCategoryName());
        SiteUser author = this.userService.findByUsername(principal.getName());

        this.articleService.create(category, articleForm.getSubject(), articleForm.getContent(), author, thumbnail);

        return String.format("redirect:/article/%s", articleForm.getCategoryName());
    }

    // detail getMapping_fix_need
    @GetMapping("/detail/{id}")
    public String articleDetail(Model model, @PathVariable("id") Long id, AnswerForm answerForm, @RequestParam(value = "page", defaultValue = "0") int page) {
        Article article = this.articleService.getArticle(id);
        // raise hit
        this.articleService.hitArticle(article);

        // answerList, replyList
        Page<Answer> answerPaging = this.answerService.getAnswerList(page, article);
        Page<Reply> replyPaging = this.replyService.getReplyList(page, article);

        // send answerList, replyList to article/detail page
        model.addAttribute("answerPaging", answerPaging);
        model.addAttribute("replyPaging", replyPaging);
        model.addAttribute("article", article);
        return "article/detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String articleVote(@PathVariable("id") Long articleId, Principal principal) {
        Article article = this.articleService.getArticle(articleId);
        SiteUser siteUser = this.userService.findByUsername(principal.getName());
        this.articleService.voteArticle(article, siteUser);
        //raise likes
        this.articleService.likesArticle(article);
        return String.format("redirect:/article/detail/%s", articleId);
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String articleModify(ArticleForm articleForm, @PathVariable("id") Long id, Model model) {
        Article article = this.articleService.getArticle(id);

        // article subject
        articleForm.setSubject(article.getSubject());
        // article content
        articleForm.setContent(article.getContent());
        // article category
        articleForm.setCategoryName(article.getCategory().getCategoryName());
        return "article/form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String articleModify(@Valid ArticleForm articleForm, BindingResult bindingResult, @PathVariable("id") Long articleId) {
        if (bindingResult.hasErrors()) {
            return "article/form";
        }
        Article article = this.articleService.getArticle(articleId);
        Category category = this.categoryService.getCategory(articleForm.getCategoryName());

        this.articleService.modify(category, article, articleForm.getSubject(), articleForm.getContent());

        return String.format("redirect:/article/detail/%s", articleId);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String articleDelete(@PathVariable("id") Long id) {
        Article article = this.articleService.getArticle(id);
        String categoryName = article.getCategory().getCategoryName();
        if (article.getAnswerList() != null) {
            this.answerService.deleteByAuthorId(id);
        }
        if (article.getReplyList() != null) {
            this.replyService.deleteByAuthorId(id);
        }
        this.articleService.delete(article);


        return String.format("redirect:/article/%s", categoryName);
    }
}
