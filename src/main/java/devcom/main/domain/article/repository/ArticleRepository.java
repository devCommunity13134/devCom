package devcom.main.domain.article.repository;

import devcom.main.domain.article.entity.Article;
import devcom.main.domain.category.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Page<Article> findAll(Pageable pageable);
    Page<Article> findAllByCategory(Category category, Pageable pageable);
}
