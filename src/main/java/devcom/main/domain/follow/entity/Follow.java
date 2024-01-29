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
public class Follow extends BaseEntity {

    @OneToOne
    private SiteUser user;

    // 팔로워 user_id_list
    @OneToMany(mappedBy = "follow", cascade = CascadeType.REMOVE)
    private List<SiteUser> follwerIdList;

    // 팔로잉 user_id_list
    @OneToMany(mappedBy = "follow", cascade = CascadeType.REMOVE)
    private List<SiteUser> follwingIdList;

}
