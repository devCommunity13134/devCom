package devcom.main.domain.article.repository;


import devcom.main.domain.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepositoy extends JpaRepository<Article,Integer> {
}
