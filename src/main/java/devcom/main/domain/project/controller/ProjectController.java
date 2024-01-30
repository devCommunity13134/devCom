package devcom.main.domain.project.controller;

import devcom.main.domain.project.ProjectCreateForm;
import devcom.main.domain.project.ProjectModifyForm;
import devcom.main.domain.project.dto.ProjectDetailDto;
import devcom.main.domain.project.entity.Project;
import devcom.main.domain.projectState.entity.ProjectState;
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
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/project")
public class ProjectController {

    private final TeamAndProjectService teamAndProjectService;
    private final UserService userService;

    @GetMapping("/delete/{projectId}")
    public String delete(@PathVariable("projectId") Long id, Principal principal){

        SiteUser siteUser = userService.findByUsername(principal.getName());
        Project project = teamAndProjectService.getProjectById(id,siteUser);

        teamAndProjectService.deleteProject(project);

        return "redirect:/team/detail/"+project.getTeam().getId();
    }

    @PostMapping("/modify")
    public String modify(@Valid ProjectModifyForm projectModifyForm,BindingResult bindingResult, Principal principal,Model model){

        SiteUser siteUser = userService.findByUsername(principal.getName());
        Project project = teamAndProjectService.getProjectById(projectModifyForm.getId(),siteUser);

        bindingResult = teamAndProjectService.modifyProject(project,projectModifyForm,bindingResult);

        if(bindingResult.hasErrors()){
            model.addAttribute("project", project);
            return "/project/modify";
        }

        return "redirect:/project/detail/"+project.getId();
    }

    @GetMapping("/modify/{projectId}")
    public String modify(@PathVariable("projectId")Long projectId, Principal principal, Model model, ProjectModifyForm projectModifyForm){

        SiteUser siteUser = userService.findByUsername(principal.getName());
        Project project = teamAndProjectService.getProjectById(projectId,siteUser);

        model.addAttribute("project", project);

        return "/project/modify";
    }

    @GetMapping("/detail/{projectId}")
    public String detail(@PathVariable("projectId")Long projectId, Principal principal, Model model){

        SiteUser siteUser = userService.findByUsername(principal.getName());

        Project project = teamAndProjectService.getProjectById(projectId,siteUser);

        List<ProjectState> todoList = new ArrayList<ProjectState>();
        List<ProjectState> doneList = new ArrayList<ProjectState>();
        List<ProjectState> inProgressList = new ArrayList<ProjectState>();

        for(ProjectState ps : project.getProjectStates()) {
            if(ps.getState().equals("todo")) {
                todoList.add(ps);
            }
            if(ps.getState().equals("done")) {
                doneList.add(ps);
            }
            if(ps.getState().equals("inProgress")) {
                inProgressList.add(ps);
            }
        }

        ProjectDetailDto projectDetailDto = ProjectDetailDto.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .team(project.getTeam())
                .createDate(project.getCreateDate())
                .todoList(todoList)
                .inProgressList(inProgressList)
                .doneList(doneList)
                .build();

        model.addAttribute("project", projectDetailDto);

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
    public String create(@PathVariable("teamId")Long teamId, @Valid ProjectCreateForm projectCreateForm, BindingResult bindingResult, Principal principal, Model model){

        SiteUser siteUser = userService.findByUsername(principal.getName());

        Team team = teamAndProjectService.getTeamById(teamId,siteUser);

        bindingResult = teamAndProjectService.createProject(team, projectCreateForm, bindingResult);
        if(bindingResult.hasErrors()){
            model.addAttribute("team", team);
            return "/project/create";
        }
        return "redirect:/team/detail/"+teamId;
    }

}
