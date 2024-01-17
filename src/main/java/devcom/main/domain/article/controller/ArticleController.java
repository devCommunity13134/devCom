package devcom.main.domain.article.controller;


import devcom.main.domain.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
}
