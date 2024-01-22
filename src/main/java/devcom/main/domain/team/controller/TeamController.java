package devcom.main.domain.team.controller;

import devcom.main.domain.team.TeamCreateForm;
import devcom.main.domain.team.dto.TeamDtoForList;
import devcom.main.domain.team.entity.Team;
import devcom.main.domain.team.service.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamController {
    private final TeamService teamService;

    @GetMapping("/list")
    public String teamList(Principal principal, Model model){

        List<Team> teamList = teamService.getTeamListByUser(principal);
        List<TeamDtoForList> teamDtoList = new ArrayList<>();
        for(Team team : teamList){
            TeamDtoForList teamDto = TeamDtoForList.builder()
                                        .id(team.getId())
                                        .teamAdminName(team.getTeamAdmin().getNickname())
                                        .name(team.getName())
                                        .description(team.getDescription())
                                        .createDate(team.getCreateDate()).build();

            teamDtoList.add(teamDto);
        }

        model.addAttribute("teamList",teamDtoList);

        return "/team/list";
    }

    @GetMapping("/create")
    public String teamCreateForm(TeamCreateForm teamCreateForm){
        return "/team/create";
    }

    @PostMapping("/create")
    public String createTeam(@Valid TeamCreateForm teamCreateForm, BindingResult bindingResult, Principal principal){

        bindingResult = teamService.create(teamCreateForm,bindingResult,principal);

        if(bindingResult.hasErrors()){
            return "/team/create";
        }

        return "redirect:/";
    }

}
