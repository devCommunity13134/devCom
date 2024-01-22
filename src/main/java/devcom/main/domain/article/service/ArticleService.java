package devcom.main.domain.article.service;


import devcom.main.domain.answer.entity.Answer;
import devcom.main.domain.article.entity.Article;
import devcom.main.domain.article.repository.ArticleRepository;
import devcom.main.domain.category.entity.Category;
import devcom.main.domain.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    //article create
    public void create(Category category, String subject, String content , SiteUser author) {
        Article article = Article.builder()
                .category(category)
                .subject(subject)
                .content(content)
                .author(author)
                .build();

        this.articleRepository.save(article);
    }
    //article modify
    public void modify(Article article, String subject, String content){
        Article article1 = article.toBuilder()
                .subject(subject)
                .content(content)
                .build();

        this.articleRepository.save(article1);
    }

    //article delete
    public void delete(Article article){
        // need delete reply
        this.articleRepository.delete(article);
    }

    public Article getArticle(long id) {
        Optional<Article> optionalArticle = this.articleRepository.findById(id);
        if (optionalArticle.isEmpty()) {
            throw new RuntimeException();
        }

        return optionalArticle.get();
    }

}
