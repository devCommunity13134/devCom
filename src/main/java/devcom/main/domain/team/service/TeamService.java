package devcom.main.domain.team.service;

import devcom.main.domain.team.entity.Team;
import devcom.main.domain.team.repository.TeamRepository;
import devcom.main.domain.teamMember.service.TeamMemberService;
import devcom.main.domain.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.security.Principal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    private final TeamMemberService teamMemberService;
    private final UserService userService;

    @Transactional
    public BindingResult create(Team team, BindingResult bindingResult, Principal principal) {

        // input validation
        if(bindingResult.hasErrors()){
            return bindingResult;
        }

        // isUnique name
        if(!isUnique(team.getName())){
            bindingResult.reject("이름중복","팀 이름이 중복되었습니다.");
        }

        teamRepository.save(team);

        teamMemberService.createTeamMemberAdmin(team,principal);

        return bindingResult;
    }

    private Boolean isUnique(String teamName){
        Optional<Team> team = teamRepository.findByName(teamName);
        return team.isEmpty();
    }


}
