package devcom.main.domain.follow.entity;


import devcom.main.domain.user.entity.SiteUser;
import devcom.main.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
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
    //    상속
    //    private Long id;
    //    follow 고유 id는 멤버 고유 id와 동일하게 해야함

    private List<Integer> followerId;
    // 나를 팔로우 하는 사람의 고유 아이디


    private List<Integer> followingId;
    // 내가 팔로우 하는 사람의 고유 아이디


}
