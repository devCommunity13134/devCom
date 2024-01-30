package devcom.main.domain.reply.entity;

import devcom.main.domain.answer.entity.Answer;
import devcom.main.domain.article.entity.Article;
import devcom.main.domain.user.entity.SiteUser;
import devcom.main.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Reply extends BaseEntity {

    private String content;


    @ManyToOne
    @JoinColumn
    private Answer originalAnswer;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private SiteUser author;

    @ManyToOne
    @JoinColumn
    private Article originalArticle;
}