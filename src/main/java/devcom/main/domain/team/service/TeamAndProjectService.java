package devcom.main.domain.team.service;

import devcom.main.domain.team.TeamCreateForm;
import devcom.main.domain.team.entity.Team;
import devcom.main.domain.teamMember.service.TeamMemberService;
import devcom.main.domain.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamAndProjectService {

    private final TeamService teamService;
    private final TeamMemberService teamMemberService;

    @Transactional
    public BindingResult create(TeamCreateForm teamCreateForm, BindingResult bindingResult, SiteUser siteUser) {

        // input validation
        if(bindingResult.hasErrors()){
            return bindingResult;
        }

        // isUnique name
        if(!teamService.isUnique(teamCreateForm.getName())){
            bindingResult.reject("이름중복","이미 존재하는 팀 이름 입니다.");
            return bindingResult;
        }

        Team NewTeam = teamService.create(teamCreateForm, siteUser);
        teamMemberService.createTeamMemberAdmin(NewTeam,siteUser);

        return bindingResult;
    }

    @SneakyThrows
    @Transactional
    public Team getTeamById(Long teamId, SiteUser siteUser) {
        Team team = teamService.getTeamById(teamId);

        if(!teamMemberService.isRegisteredMember(team,siteUser)){
            throw new Exception("권한이 없습니다.");
        }

        return team;
    }

    public List<Team> getTeamListByUser(SiteUser siteUser) {
        return teamService.getTeamListByUser(siteUser);
    }

}
