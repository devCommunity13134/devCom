package devcom.main.domain.follow.service;


import devcom.main.domain.follow.entity.Follow;
import devcom.main.domain.follow.repository.FollowRepository;
import devcom.main.domain.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;


    public Follow create(SiteUser user) {
        List<SiteUser> followerUserList = new ArrayList<>();
        List<SiteUser> followingUserList = new ArrayList<>();

        Follow follow = Follow.builder()
                .user(user)
                .follwerIdList(followerUserList)
                .follwingIdList(followingUserList)
                .build();

        this.followRepository.save(follow);
        return follow;
    }
}
