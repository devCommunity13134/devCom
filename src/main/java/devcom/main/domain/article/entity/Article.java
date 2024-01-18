package devcom.main.domain.article.entity;

import devcom.main.domain.category.entity.Category;
import devcom.main.domain.img.entity.Img;
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

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Article extends BaseEntity {

    private String subject;

    private String content;

    // 조회수
    @Column
    private Integer hit;

    // 좋아요
    @Column
    private Integer likes;

    @OneToOne
    @JoinColumn
    private Category category;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private SiteUser author;
}
