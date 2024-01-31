package devcom.main.domain.home;


import devcom.main.domain.article.entity.Article;
import devcom.main.domain.article.service.ArticleService;
import devcom.main.domain.category.entity.Category;
import devcom.main.domain.category.service.CategoryService;
import devcom.main.domain.user.entity.SiteUser;
import devcom.main.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;
    private final ArticleService articleService;
    private final CategoryService categoryService;

    @GetMapping("/")
    public String root(Model model, Principal principal, @RequestParam(value = "page",defaultValue = "0") int page) {
        if(principal != null) {
            SiteUser user = this.userService.findByUsername(principal.getName());
            model.addAttribute("user",user);
        }
        // sort by likes
        Page<Article> likesPage = this.articleService.getArticleListSortByLikes(page);
        // sort by hit
        Page<Article> hitPage = this.articleService.getArticleListSortByHit(page);
        // sort by commentSize
        Page<Article> commentSizePage = this.articleService.getArticleListSortByCommentSize(page);

        model.addAttribute("likesPage", likesPage);
        model.addAttribute("hitPage", hitPage);
        model.addAttribute("commentSizePage", commentSizePage);

        Category frontEnd = this.categoryService.getCategory("frontEnd");
        Category backEnd = this.categoryService.getCategory("backEnd");
        Category dataEngineer = this.categoryService.getCategory("dataEngineer");
        Category AI = this.categoryService.getCategory("AI");

        // page by categoryName
        Page<Article> frontEndPage = this.articleService.getArticleListSmallSize(page, frontEnd);
        Page<Article> backEndPage = this.articleService.getArticleListSmallSize(page, backEnd);
        Page<Article> dataEngineerPage = this.articleService.getArticleListSmallSize(page, dataEngineer);
        Page<Article> AIPage = this.articleService.getArticleListSmallSize(page, AI);

        model.addAttribute("frontEndPage", frontEndPage);
        model.addAttribute("backEndPage", backEndPage);
        model.addAttribute("dataEngineerPage", dataEngineerPage);
        model.addAttribute("AIPage", AIPage);

        return "home/index";
    }
}
