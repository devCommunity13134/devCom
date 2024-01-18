package devcom.main.domain.article.service;


import devcom.main.domain.article.entity.Article;
import devcom.main.domain.article.repository.ArticleRepositoy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepositoy articleRepositoy;



    public void create(String subject, String content) {
        Article article = Article.builder()
                                    .subject(subject)
                                    .content(content)
                                    .build();

        this.articleRepositoy.save(article);
    }

}
