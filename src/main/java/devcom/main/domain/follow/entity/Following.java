package devcom.main.domain.follow.entity;


import devcom.main.domain.user.entity.SiteUser;
import devcom.main.global.jpa.BaseEntity;
import jakarta.persistence.*;
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
public class Following extends BaseEntity {

    @ManyToOne
    // 내가
    private SiteUser user;

    // 팔로잉 하는 user_id_list
    private Long followingUserId;

}
