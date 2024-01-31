package devcom.main.domain.teamMember.controller;

import devcom.main.domain.team.entity.Team;
import devcom.main.domain.team.service.TeamAndProjectService;
import devcom.main.domain.teamInvite.TeamInviteForm;
import devcom.main.domain.teamMember.entity.TeamMember;
import devcom.main.domain.user.entity.SiteUser;
import devcom.main.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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
    @SneakyThrows
    public String getTeamMember(@PathVariable("teamId") Long teamId, Model model, Principal principal, TeamInviteForm teamInviteForm) {

        SiteUser siteUser = userService.findByUsername(principal.getName());
        Team team = teamAndProjectService.getTeamById(teamId,siteUser);

        if(!team.getTeamAdmin().getUsername().equals(siteUser.getUsername())){
            throw new Exception("권한이 없습니다.");
        }
        
        model.addAttribute("team", team);
        return "teamMember/list";
    }

    @GetMapping("/changeAdmin/{teamMemberId}")
    public String changeAdmin(@PathVariable("teamMemberId") Long teamMemberId, Principal principal) {

        SiteUser siteUser = userService.findByUsername(principal.getName());
        TeamMember teamMember = teamAndProjectService.getTeamMemberById(teamMemberId);

        teamAndProjectService.changeTeamAdmin(teamMember,siteUser);

        return "redirect:/team/detail/" + teamMember.getTeam().getId();
    }

    @GetMapping("/delete/{teamMemberId}")
    public String deleteTeamMember(@PathVariable("teamMemberId") Long teamMemberId, Principal principal) {

        SiteUser siteUser = userService.findByUsername(principal.getName());
        TeamMember teamMember = teamAndProjectService.getTeamMemberById(teamMemberId);

        teamAndProjectService.deleteTeamMember(teamMember,siteUser);

        return "redirect:/teamMember/" + teamMember.getTeam().getId();
    }

}
