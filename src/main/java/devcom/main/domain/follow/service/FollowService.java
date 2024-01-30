package devcom.main.domain.follow.service;


import devcom.main.domain.follow.entity.Follower;
import devcom.main.domain.follow.entity.Following;
import devcom.main.domain.follow.repository.FollowerRepository;
import devcom.main.domain.follow.repository.FollowingRepository;
import devcom.main.domain.skill.service.SkillService;
import devcom.main.domain.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowingRepository followingRepository;

    private final FollowerRepository followerRepository;


    public void addFollower(SiteUser user, Long id) {
        List<Long> follwerIdList = new ArrayList<>();
        follwerIdList.add(id);
        Follower follower = Follower.builder()
                .user(user)
                .followerUserIdList(follwerIdList)
                .build();
        this.followerRepository.save(follower);
    }

}
