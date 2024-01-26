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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamMemberService {

    private final TeamMemberRepository teamMemberRepository;

    @Transactional
    public void createTeamMemberAdmin(Team team, SiteUser teamAdminUser) {

        TeamMember tm = TeamMember.builder()
                .team(team)
                .siteUser(teamAdminUser)
                .build();

        teamMemberRepository.save(tm);
    }

    public boolean isRegisteredMember(Team team, SiteUser siteUser){

        Optional<TeamMember> teamMember = teamMemberRepository.findByTeamAndSiteUser(team,siteUser);

        return teamMember.isPresent();
    }
}
