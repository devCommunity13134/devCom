package devcom.main.domain.article.controller;


import devcom.main.domain.article.entity.Article;
import devcom.main.domain.article.repository.ArticleRepository;
import devcom.main.domain.article.service.ArticleService;
import devcom.main.domain.category.entity.Category;
import devcom.main.domain.category.service.CategoryService;
import devcom.main.global.rq.Rq;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;
    private final CategoryService categoryService;

    // article list
    @GetMapping("/list/{category}")
    public String articleList(Model model, @PathVariable("category") String category, @RequestParam(value = "page",defaultValue = "0") int page){
        Category category1 = this.categoryService.getCategory(category);
        Page<Article> paging = this.articleService.getArticleList(page,category1);
        model.addAttribute("paging",paging);
        return "article/list";
    }
    // create article
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String articleCreate(){

        return "article/form";
    }
    // detail getMapping_fix_need
    @GetMapping("/detail/1")
    public String articleDetail(){

        return "article/detail";
    }
}
