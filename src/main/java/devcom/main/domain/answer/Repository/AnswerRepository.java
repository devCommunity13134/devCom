package devcom.main.domain.answer.Repository;

import devcom.main.domain.answer.entity.Answer;
import devcom.main.domain.article.entity.Article;
import devcom.main.domain.category.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnswerRepository extends JpaRepository<Answer,Long> {
    Optional<Answer> deleteByAuthorId(Long AuthorId);

    Page<Answer> findAllByOriginalArticle(Article article, Pageable pageable);
}
