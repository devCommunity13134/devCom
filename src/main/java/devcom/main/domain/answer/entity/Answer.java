package devcom.main.domain.answer.entity;


import devcom.main.domain.article.entity.Article;
import devcom.main.domain.user.entity.SiteUser;
import devcom.main.global.jpa.BaseEntity;
import jakarta.persistence.*;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Answer extends BaseEntity {

    private String content;

    @Column
    @Builder.Default()
    private Integer likes = 0;

    @ManyToOne
    @JoinColumn
    private Article originalArticle;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private SiteUser author;

    @ManyToMany
    Set<SiteUser> voter;

}
