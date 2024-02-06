package devcom.main.global.rq;

import devcom.main.domain.follow.entity.Follower;
import devcom.main.domain.follow.entity.Following;
import devcom.main.domain.follow.service.FollowService;
import devcom.main.domain.team.entity.Team;
import devcom.main.domain.team.service.TeamAndProjectService;
import devcom.main.domain.user.entity.SiteUser;
import devcom.main.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

@Component
@RequestScope
public class Rq {
    private final UserService userService;
    private final TeamAndProjectService teamAndProjectService;
    private final FollowService followService;
    private final HttpServletRequest req;
    private final HttpServletResponse resp;
    private final HttpSession session;
    private User user;
    @Setter
    private SiteUser siteUser = null;

    @Setter
    List<Team> teamList = null;

    @Setter
    List<SiteUser> followerList = null;

    @Setter
    List<SiteUser> followingList = null;

    public Rq(UserService userService, TeamAndProjectService teamAndProjectService, FollowService followService, HttpServletRequest req, HttpServletResponse resp, HttpSession session) {
        this.userService = userService;
        this.teamAndProjectService = teamAndProjectService;
        this.followService = followService;
        this.req = req;
        this.resp = resp;
        this.session = session;

        // 현재 로그인한 회원의 인증정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof User) {
            this.user = (User) authentication.getPrincipal();
        } else {
            this.user = null;
        }
    }



    public boolean isLogin() {
        return user != null;
    }

    public boolean isLogout() {
        return !isLogin();
    }

    public SiteUser getSiteUser() {
        if (isLogout()) {
            return null;
        }

        if (siteUser == null) {
            siteUser = userService.findByUsername(getLoginedSiteUserUsername());
        }

        return siteUser;
    }

    public List<Team> getTeamList() {
        if (isLogout()) {
            return null;
        }

        if (siteUser != null) {
            teamList = teamAndProjectService.getTeamListByUser(siteUser);
        }
        return teamList;
    }

    public List<SiteUser> getFollowerList() {
        if (isLogout()) {
            return null;
        }

        if (siteUser != null) {
            followerList = followService.getFollowerUserList(siteUser);
        }
        return followerList;
    }

    public List<SiteUser> getFollowingList() {
        if (isLogout()) {
            return null;
        }

        if (siteUser != null) {
            followingList = followService.getFollowingUserList(siteUser);
        }
        return followingList;
    }

    private String getLoginedSiteUserUsername() {
        if (isLogout()) return null;

        return user.getUsername();
    }

    public boolean isThereNewMessage() {
        if (isLogout()) return true;

        SiteUser user = this.getSiteUser();
        for ( int i = 0 ; i < user.getReceiveMessageList().size(); i++ ) {
            if(!user.getReceiveMessageList().get(i).isChecked()) {
                return false;
            }
        }
        return true;
    }

}
