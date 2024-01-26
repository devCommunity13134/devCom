package devcom.main.domain.article.entity;

import devcom.main.domain.answer.entity.Answer;
import devcom.main.domain.category.entity.Category;
import devcom.main.domain.img.entity.Img;
import devcom.main.domain.reply.entity.Reply;
import devcom.main.domain.skill.entity.Skill;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Article extends BaseEntity {

    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    // 조회수
    @Column
    @Builder.Default()
    private Integer hit = 0;

    // 좋아요
    @Column
    @Builder.Default()
    private Integer likes = 0;

    @ManyToOne
    @JoinColumn
    private Category category;

    @OneToMany(mappedBy = "originalArticle", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;

    @OneToMany(mappedBy = "originalArticle", cascade = CascadeType.REMOVE)
    private List<Reply> replyList;


    @ManyToOne
    @JoinColumn(name = "author_id")
    private SiteUser author;

    @ManyToMany
    Set<SiteUser> voter;
}
