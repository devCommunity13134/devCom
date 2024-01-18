package devcom.main.domain.article.entity;

import devcom.main.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Getter
@SuperBuilder
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Article extends BaseEntity {

    private String subject;

    private String content;

}
