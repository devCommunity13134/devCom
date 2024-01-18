package devcom.main.domain.article.entity;

import devcom.main.domain.category.entity.Category;
import devcom.main.domain.img.entity.Img;
import devcom.main.domain.user.entity.SiteUser;
import devcom.main.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Article extends BaseEntity {

    private String subject;

    private String content;

    // 조회수
    private int hit;

    // 좋아요
    private int like;

    @OneToOne
    private Category category;

    @OneToMany(mappedBy = "img_url")
    private List<Img> img_url;

    @ManyToOne
    private SiteUser author;
}
