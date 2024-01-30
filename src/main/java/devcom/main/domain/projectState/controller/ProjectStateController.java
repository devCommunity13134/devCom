package devcom.main.domain.projectState.controller;

import devcom.main.domain.project.entity.Project;
import devcom.main.domain.projectState.ProjectStateCreateForm;
import devcom.main.domain.team.service.TeamAndProjectService;
import devcom.main.domain.user.entity.SiteUser;
import devcom.main.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/projectState")
public class ProjectStateController {

    private final TeamAndProjectService teamAndProjectService;
    private final UserService userService;

    @PostMapping("/createProjectState")
    public String createProjectState(@Valid ProjectStateCreateForm projectStateCreateForm, BindingResult bindingResult, Principal principal){

        SiteUser siteUser = userService.findByUsername(principal.getName());

        Project project = teamAndProjectService.getProjectById(projectStateCreateForm.getProjectId(),siteUser);

        teamAndProjectService.createProjectState(project,projectStateCreateForm);

        return "redirect:/project/detail/"+project.getId();
    }

    @GetMapping("/delete/{projectId}/{projectStateId}")
    public String delete(@PathVariable("projectId") Long projectId,@PathVariable("projectStateId") Long projectStateId, Principal principal){

        SiteUser siteUser = userService.findByUsername(principal.getName());

        Project project = teamAndProjectService.getProjectById(projectId,siteUser);

        teamAndProjectService.deleteProjectState(projectStateId);

        return "redirect:/project/detail/"+project.getId();
    }

}
