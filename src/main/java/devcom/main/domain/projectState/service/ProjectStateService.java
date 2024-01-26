package devcom.main.domain.projectState.service;

import devcom.main.domain.project.entity.Project;
import devcom.main.domain.projectState.ProjectStateCreateForm;
import devcom.main.domain.projectState.entity.ProjectState;
import devcom.main.domain.projectState.repository.ProjectStateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectStateService {
    private final ProjectStateRepository projectStateRepository;


    public void create(Project project, ProjectStateCreateForm projectStateCreateForm) {

        ProjectState projectState = ProjectState.builder()
                .project(project)
                .content(projectStateCreateForm.getContent())
                .state(projectStateCreateForm.getState())
                .build();

        projectStateRepository.save(projectState);
    }

    public ProjectState findById(Long projectStateId) {
        return projectStateRepository.findById(projectStateId).get();
    }

    public void delete(ProjectState ps) {
        projectStateRepository.delete(ps);
    }
}
