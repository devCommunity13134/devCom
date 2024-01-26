package devcom.main.domain.article.controller;


import devcom.main.domain.article.ArticleForm;
import devcom.main.domain.article.entity.Article;
import devcom.main.domain.article.repository.ArticleRepository;
import devcom.main.domain.article.service.ArticleService;
import devcom.main.domain.category.entity.Category;
import devcom.main.domain.category.service.CategoryService;
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

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;
    private final CategoryService categoryService;
    private final UserService userService;

    // article list
    @GetMapping("/{category}")
    public String articleList(Model model, @PathVariable("category") String category, @RequestParam(value = "page",defaultValue = "0") int page){
        Category category1 = this.categoryService.getCategory(category);
        Page<Article> paging = this.articleService.getArticleList(page,category1);
        model.addAttribute("paging",paging);
        model.addAttribute("categoryName", category1.getCategoryName());
        return "article/list";
    }
    // create article
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String articleCreate(){

        return "article/form";
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String articleCreate(@Valid ArticleForm articleForm, BindingResult bindingResult, Principal principal){
        if(bindingResult.hasErrors()){
            return "article/form";
        }
        Category category = this.categoryService.getCategory(articleForm.getCategoryName());
        SiteUser author = this.userService.findByUsername(principal.getName());

        this.articleService.create(category ,articleForm.getSubject(),articleForm.getContent(),author);

        return String.format("redirect:/article/%s",articleForm.getCategoryName());
    }

    // detail getMapping_fix_need
    @GetMapping("/detail/{id}")
    public String articleDetail(Model model, @PathVariable("id") Long id){
        Article article = this.articleService.getArticle(id);
        model.addAttribute("article",article);
        return "article/detail";
    }
}
