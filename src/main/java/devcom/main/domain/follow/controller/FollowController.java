package devcom.main.domain.follow.controller;


import devcom.main.domain.follow.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @GetMapping("/follow")
    public String followUser() {
        return "redirect:/";
    }

}
