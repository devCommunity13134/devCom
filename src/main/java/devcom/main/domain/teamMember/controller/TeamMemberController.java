package devcom.main.domain.teamMember.controller;

import devcom.main.domain.team.entity.Team;
import devcom.main.domain.teamMember.entity.TeamMember;
import devcom.main.domain.teamMember.service.TeamMemberService;
import devcom.main.domain.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/teamMember")
public class TeamMemberController {
    private final TeamMemberService teamMemberService;

}
