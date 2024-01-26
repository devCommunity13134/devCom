package devcom.main.domain.project.controller;

import devcom.main.domain.project.ProjectCreateForm;
import devcom.main.domain.project.entity.Project;
import devcom.main.domain.team.entity.Team;
import devcom.main.domain.team.service.TeamAndProjectService;
import devcom.main.domain.user.entity.SiteUser;
import devcom.main.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/project")
public class ProjectController {

    private final TeamAndProjectService teamAndProjectService;
    private final UserService userService;

    @GetMapping("/detail/{projectId}")
    public String detail(@PathVariable("projectId")Long projectId, Principal principal, Model model){

        SiteUser siteUser = userService.findByUsername(principal.getName());

        Project project = teamAndProjectService.getProjectById(projectId,siteUser);

        model.addAttribute("project", project);

        return "/project/detail";
    }

    @GetMapping("/create/{teamId}")
    public String create(@PathVariable("teamId")Long teamId, ProjectCreateForm projectCreateForm, Principal principal, Model model){

        SiteUser siteUser = userService.findByUsername(principal.getName());

        Team team = teamAndProjectService.getTeamById(teamId,siteUser);

        model.addAttribute("team", team);

        return "/project/create";
    }

    @PostMapping("/create/{teamId}")
    public String create(@PathVariable("teamId")Long teamId, @Valid ProjectCreateForm projectCreateForm, BindingResult bindingResult, Principal principal){

        SiteUser siteUser = userService.findByUsername(principal.getName());

        Team team = teamAndProjectService.getTeamById(teamId,siteUser);

        bindingResult = teamAndProjectService.createProject(team, projectCreateForm, bindingResult);

        return "redirect:/team/detail/"+teamId;
    }

}
