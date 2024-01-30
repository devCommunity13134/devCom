package devcom.main.domain.teamMember.controller;

import devcom.main.domain.team.entity.Team;
import devcom.main.domain.team.service.TeamAndProjectService;
import devcom.main.domain.teamInvite.TeamInviteForm;
import devcom.main.domain.user.entity.SiteUser;
import devcom.main.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/teamMember")
public class TeamMemberController {
    private final UserService userService;
    private final TeamAndProjectService teamAndProjectService;

    @GetMapping("/{teamId}")
    public String getTeamMember(@PathVariable("teamId") Long teamId, Model model, Principal principal, TeamInviteForm teamInviteForm) {

        SiteUser siteUser = userService.findByUsername(principal.getName());
        Team team = teamAndProjectService.getTeamById(teamId,siteUser);

        model.addAttribute("team", team);
        return "teamMember/list";
    }


}
