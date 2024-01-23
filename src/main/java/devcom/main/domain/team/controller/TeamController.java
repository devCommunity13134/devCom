package devcom.main.domain.team.controller;

import devcom.main.domain.team.TeamCreateForm;
import devcom.main.domain.team.dto.TeamDtoForList;
import devcom.main.domain.team.entity.Team;
import devcom.main.domain.team.service.TeamService;
import devcom.main.domain.teamMember.service.TeamMemberService;
import devcom.main.domain.user.entity.SiteUser;
import devcom.main.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamController {

    private final TeamService teamService;

    private final UserService userService;

    @GetMapping("/detail/{id}")
    public String teamDetail(@PathVariable("id") Long teamId,Principal principal, Model model){

        SiteUser siteUser = userService.findByusername(principal.getName());

        Team team = teamService.getTeamById(teamId,siteUser);

        model.addAttribute("team", team);

        return "/team/detail";
    }

    @GetMapping("/list")
    public String teamList(Principal principal, Model model){

        SiteUser siteUser = userService.findByusername(principal.getName());

        List<Team> teamList = teamService.getTeamListByUser(siteUser);
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

        SiteUser siteUser = userService.findByusername(principal.getName());

        bindingResult = teamService.create(teamCreateForm,bindingResult,siteUser);


        if(bindingResult.hasErrors()){
            return "/team/create";
        }

        return "redirect:/";
    }

}
