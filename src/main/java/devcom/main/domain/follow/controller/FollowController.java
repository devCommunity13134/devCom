package devcom.main.domain.follow.controller;


import devcom.main.domain.follow.entity.Following;
import devcom.main.domain.follow.service.FollowService;
import devcom.main.domain.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;



    public Following create(SiteUser user) {
        return this.followService.create(user);
    }

}
