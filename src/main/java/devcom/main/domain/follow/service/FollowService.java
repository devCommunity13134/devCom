package devcom.main.domain.follow.service;


import devcom.main.domain.follow.entity.Follower;
import devcom.main.domain.follow.entity.Following;
import devcom.main.domain.follow.repository.FollowerRepository;
import devcom.main.domain.follow.repository.FollowingRepository;
import devcom.main.domain.skill.service.SkillService;
import devcom.main.domain.user.entity.SiteUser;
import devcom.main.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowingRepository followingRepository;

    private final FollowerRepository followerRepository;

    private final UserService userService;


    public void addFollower(SiteUser user, Long id) {
        // 팔로우를 누르면 상대방의 팔로워에 내가 저장되어야 한다.
        // user = 현재 로그인한 유저(나)
        // id = 내가 팔로우를 누른 대상의 회원 id(상대)
        Follower follower = Follower.builder()
                .user(this.userService.findById(id))
                .followerUserId(user.getId())
                .build();
        this.followerRepository.save(follower);
        this.userService.findById(id).getFollowerList().add(follower);
    }

    public void addFollowing(SiteUser user, Long id) {
        // 팔로우를 누르면 나의 팔로잉에 상대가 저장되어야 한다.
        // user = 현재 로그인한 유저(나)
        // id = 나를 팔로우 하는 회원 id(상대)
        Following following = Following.builder()
                .user(user)
                .followingUserId(id)
                .build();
        this.followingRepository.save(following);
        user.getFollowingList().add(following);
    }

    // user를 팔로우 하는 팔로워 유저 리스트
    public List<SiteUser> getFollowerUserList(SiteUser user) {
        List<SiteUser> followerUserList = new ArrayList<>();

        for(int i = 0; i < user.getFollowerList().size(); i++) {
            followerUserList.add(this.userService.findById(user.getFollowerList().get(i).getFollowerUserId()));
        }
        return followerUserList;
    }

    // user가 팔로잉 하는 유저 리스트
    public List<SiteUser> getFollowingUserList(SiteUser user) {
        List<SiteUser> followingUserList = new ArrayList<>();

        for(int i = 0; i < user.getFollowingList().size(); i++) {
            followingUserList.add(this.userService.findById(user.getFollowingList().get(i).getFollowingUserId()));
        }
        return followingUserList;
    }

}
