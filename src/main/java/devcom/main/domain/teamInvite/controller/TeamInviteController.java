package devcom.main.domain.teamInvite.controller;

import devcom.main.domain.team.entity.Team;
import devcom.main.domain.team.service.TeamAndProjectService;
import devcom.main.domain.teamInvite.TeamInviteForm;
import devcom.main.domain.user.entity.SiteUser;
import devcom.main.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/teamInvite")
public class TeamInviteController {

    private final UserService userService;
    private final TeamAndProjectService teamAndProjectService;

    @PostMapping("/inviteMember")
    @ResponseBody
    public String inviteMember(@Valid @RequestBody TeamInviteForm teamInviteForm, BindingResult bindingResult, Principal principal) {

        if(bindingResult.hasErrors()){
            return "에러다 에러 이놈아";
        }

        return "/teamMember/list";
    }
}
