package devcom.main.domain.img.entity;

import devcom.main.domain.article.entity.Article;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Img {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String img_url;

}
