package devcom.main.domain.team.controller;

import devcom.main.domain.team.TeamCreateForm;
import devcom.main.domain.team.TeamModifyForm;
import devcom.main.domain.team.entity.Team;
import devcom.main.domain.team.service.TeamAndProjectService;
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

    private final TeamAndProjectService teamAndProjectService;
    private final UserService userService;

    @PostMapping("/modifyTeam")
    public String modifyTeam(@Valid TeamModifyForm teamModifyForm, BindingResult bindingResult, Principal principal){

        SiteUser siteUser = userService.findByUsername(principal.getName());

        bindingResult = teamAndProjectService.modifyTeam(teamModifyForm,bindingResult,siteUser);

        if(bindingResult.hasErrors()){
            return "/team/modifyTeam";
        }

        return "redirect:/team/detail/"+teamModifyForm.getId();
    }

    @GetMapping("/modifyTeam/{id}")
    public String modifyTeam(@PathVariable("id") Long teamId, TeamModifyForm teamModifyForm, Principal principal, Model model){

        SiteUser siteUser = userService.findByUsername(principal.getName());

        Team team = teamAndProjectService.getTeamById(teamId,siteUser);

        model.addAttribute("team", team);

        return "/team/modifyTeam";
    }

    @GetMapping("/detail/{id}")
    public String teamDetail(@PathVariable("id") Long teamId,Principal principal, Model model){

        SiteUser siteUser = userService.findByUsername(principal.getName());

        Team team = teamAndProjectService.getTeamById(teamId,siteUser);

        model.addAttribute("team", team);

        return "/team/detail";
    }

    @GetMapping("/list")
    public String teamList(Principal principal, Model model){

        SiteUser siteUser = userService.findByUsername(principal.getName());

        List<Team> teamList = teamAndProjectService.getTeamListByUser(siteUser);
       
        model.addAttribute("teamList",teamList);

        return "team/list";
    }

    @GetMapping("/create")
    public String teamCreateForm(TeamCreateForm teamCreateForm){
        return "/team/create";
    }

    @PostMapping("/create")
    public String createTeam(@Valid TeamCreateForm teamCreateForm, BindingResult bindingResult, Principal principal){

        SiteUser siteUser = userService.findByUsername(principal.getName());

        bindingResult = teamAndProjectService.createTeam(teamCreateForm,bindingResult,siteUser);

        if(bindingResult.hasErrors()){
            return "/team/create";
        }

        return "redirect:/team/list";
    }

}
