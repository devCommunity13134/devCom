package devcom.main.domain.answer.entity;


import devcom.main.domain.article.entity.Article;
import devcom.main.domain.user.entity.SiteUser;
import devcom.main.global.jpa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Answer extends BaseEntity {

    private String content;
    @Column
    private Integer likes;

    @ManyToOne
    @JoinColumn
    private Article originalArticle;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private SiteUser author;

}
