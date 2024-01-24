package devcom.main.domain.article.controller;


import devcom.main.domain.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/article")
public class ArticleController {

    private final ArticleRepository articleRepository;

    // article list getMapping_fix_need
    @GetMapping("/list")
    public String articleList(){

        return "article/list";
    }
    // create article
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
