package devcom.main.domain.answer.entity;

import devcom.main.domain.article.entity.Article;
import devcom.main.domain.reply.entity.Reply;
import devcom.main.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Answer extends BaseEntity {

    private String content;

    private int like;

    @ManyToOne
    private Article originalArticle;

    @OneToMany
    private Reply reply;
}
