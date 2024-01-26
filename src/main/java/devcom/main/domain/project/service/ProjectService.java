package devcom.main.domain.project.service;

import devcom.main.domain.project.ProjectCreateForm;
import devcom.main.domain.project.ProjectModifyForm;
import devcom.main.domain.project.entity.Project;
import devcom.main.domain.project.repository.ProjectRepository;
import devcom.main.domain.team.entity.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    public Optional<Project> findById(Long projectId){
        return projectRepository.findById(projectId);
    }

    public Optional<Project> findByTeamAndName(Team team,String name){
        return projectRepository.findByTeamAndName(team,name);
    }

    public Boolean isUniqueProjectName(Team team,String name){
        return projectRepository.findByTeamAndName(team,name).isEmpty();
    }

    public void createProject(Team team, ProjectCreateForm projectCreateForm) {

        Project project = Project.builder()
                .name(projectCreateForm.getName())
                .description(projectCreateForm.getDescription())
                .team(team)
                .build();

        projectRepository.save(project);
    }

    public Optional<Project> getProjectById(Long projectId) {
        return projectRepository.findById(projectId);
    }

    public void modifyProject(Project project, ProjectModifyForm projectModifyForm) {

        project = project.toBuilder()
                .name(projectModifyForm.getName())
                .description(projectModifyForm.getDescription())
                .build();

        projectRepository.save(project);
    }
}
