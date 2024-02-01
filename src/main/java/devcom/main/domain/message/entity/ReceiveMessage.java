package devcom.main.domain.message.entity;

import devcom.main.domain.user.entity.SiteUser;
import devcom.main.global.jpa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class ReceiveMessage extends BaseEntity {

    @ManyToOne
    private SiteUser user;
    // 보낸 사람

    private Long receiveUserId;
    // 받는 사람 id(pk)

    @Column(columnDefinition = "TEXT")
    private String content;

}
