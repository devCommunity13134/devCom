package devcom.main.domain.article.service;


import devcom.main.domain.answer.entity.Answer;
import devcom.main.domain.article.entity.Article;
import devcom.main.domain.article.repository.ArticleRepository;
import devcom.main.domain.category.entity.Category;
import devcom.main.domain.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
    public void modify(Category category, Article article, String subject, String content){
        Article article1 = article.toBuilder()
                .category(category)
                .subject(subject)
                .content(content)
                .build();

        this.articleRepository.save(article1);
    }

    //article delete
    public void delete(Article article){

        this.articleRepository.delete(article);
    }

    public Article getArticle(long id) {
        Optional<Article> optionalArticle = this.articleRepository.findById(id);
        if (optionalArticle.isEmpty()) {
            throw new RuntimeException();
        }

        return optionalArticle.get();
    }
    public Page<Article> getArticleList(int page){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.articleRepository.findAll(pageable);
    }

    public Page<Article> getArticleList(int page, Category category){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.articleRepository.findAllByCategory(category,pageable);
    }
    // same as above, smaller size
    public Page<Article> getArticleListSmallSize(int page, Category category){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 4, Sort.by(sorts));
        return this.articleRepository.findAllByCategory(category,pageable);
    }


    // main page top section list
    public Page<Article> getArticleListSortByLikes(int page){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("likes"));
        Pageable pageable = PageRequest.of(page, 4, Sort.by(sorts));
        return this.articleRepository.findAll(pageable);
    }

    public Page<Article> getArticleListSortByHit(int page){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("hit"));
        Pageable pageable = PageRequest.of(page, 4, Sort.by(sorts));
        return this.articleRepository.findAll(pageable);
    }

    public Page<Article> getArticleListSortByCommentSize(int page){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("commentSize"));
        Pageable pageable = PageRequest.of(page, 4, Sort.by(sorts));
        return this.articleRepository.findAll(pageable);
    }


    public void voteArticle(Article article, SiteUser siteUser){
        article.getVoter().add(siteUser);
        this.articleRepository.save(article);
    }

    // raise hit
    public void hitArticle(Article article){
        Article aritlce1 = article.toBuilder()
                .hit(article.getHit()+1)
                .build();
        this.articleRepository.save(aritlce1);
    }
    //
    public void likesArticle(Article article){
        Article aritlce1 = article.toBuilder()
                .likes(article.getLikes()+1)
                .build();
        this.articleRepository.save(aritlce1);
    }

    public void raiseCommentSize(Article article){
        Article article1 = article.toBuilder()
                .commentSize(article.getCommentSize()+1)
                .build();

        this.articleRepository.save(article1);
    }
}
