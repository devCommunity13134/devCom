package devcom.main.domain.reply.repository;

import devcom.main.domain.answer.entity.Answer;
import devcom.main.domain.article.entity.Article;
import devcom.main.domain.reply.entity.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {
    Optional<Reply> deleteByAuthorId(Long AuthorId);

    Page<Reply> findAllByOriginalAnswer(Answer answer, Pageable pageable);
}
