package devcom.main.domain.teamMember.service;

import devcom.main.domain.team.entity.Team;
import devcom.main.domain.teamMember.repository.TeamMemberRepository;
import devcom.main.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class TeamMemberService {

    private final TeamMemberRepository teamMemberRepository;
    private final UserService userService;

    public void createTeamMemberAdmin(Team team, Principal principal) {

    }
}
