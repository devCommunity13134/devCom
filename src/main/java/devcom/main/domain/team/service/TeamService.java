package devcom.main.domain.team.service;

import devcom.main.domain.team.TeamCreateForm;
import devcom.main.domain.team.entity.Team;
import devcom.main.domain.team.repository.TeamRepository;
import devcom.main.domain.teamMember.service.TeamMemberService;
import devcom.main.domain.user.entity.SiteUser;
import devcom.main.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    private final TeamMemberService teamMemberService;
    private final UserService userService;

    @Transactional
    public BindingResult create(TeamCreateForm teamCreateForm, BindingResult bindingResult, Principal principal) {

        // input validation
        if(bindingResult.hasErrors()){
            return bindingResult;
        }

        // isUnique name
        if(!isUnique(teamCreateForm.getName())){
            bindingResult.reject("이름중복","이미 존재하는 팀 이름 입니다.");
            return bindingResult;
        }

        SiteUser teamAdminUser = userService.findByusername(principal.getName());

        Team NewTeam = Team.builder()
                .name(teamCreateForm.getName())
                .description(teamCreateForm.getDescription())
                .teamAdmin(teamAdminUser)
                .build();

        teamRepository.save(NewTeam);

        teamMemberService.createTeamMemberAdmin(NewTeam,teamAdminUser);

        return bindingResult;
    }

    private Boolean isUnique(String teamName){
        Optional<Team> team = teamRepository.findByName(teamName);
        return team.isEmpty();
    }

    @Transactional(readOnly = true)
    public List<Team> getTeamListByUser(Principal principal) {
        SiteUser siteUser = userService.findByusername(principal.getName());
        return teamRepository.findByUser(siteUser.getId());
    }
}
