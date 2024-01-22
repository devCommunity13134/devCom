package devcom.main.domain.teamMember.service;

import devcom.main.domain.team.entity.Team;
import devcom.main.domain.teamMember.entity.TeamMember;
import devcom.main.domain.teamMember.repository.TeamMemberRepository;
import devcom.main.domain.user.entity.SiteUser;
import devcom.main.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamMemberService {

    private final TeamMemberRepository teamMemberRepository;
    private final UserService userService;

    @Transactional
    public void createTeamMemberAdmin(Team team, SiteUser teamAdminUser) {

        // 최초 팀 생성시 만든사람은 팀관리자
        TeamMember tm = TeamMember.builder()
                .team(team)
                .siteUser(teamAdminUser)
                .authority("teamAdmin").build();

        teamMemberRepository.save(tm);
    }
}
