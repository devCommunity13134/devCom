package devcom.main.domain.follow.controller;


import devcom.main.domain.follow.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

}
