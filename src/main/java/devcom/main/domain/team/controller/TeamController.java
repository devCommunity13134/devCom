package devcom.main.domain.team.controller;

import devcom.main.domain.team.entity.Team;
import devcom.main.domain.team.service.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamController {
    private final TeamService teamService;

    @GetMapping("/create")
    public String teamCreateForm(){
        return "/team/create";
    }

    @PostMapping("/create")
    public String createTeam(@Valid Team team, BindingResult bindingResult, Principal principal){

        bindingResult = teamService.create(team,bindingResult,principal);

        if(bindingResult.hasErrors()){
            return "/team/create";
        }

        return "/";
    }

}
