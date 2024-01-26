package devcom.main.domain.project.dto;

import devcom.main.domain.projectState.entity.ProjectState;
import devcom.main.domain.team.entity.Team;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ProjectDetailDto {

    private Long id;

    private LocalDateTime createDate;

    private String name;

    private String description;

    private Team team;

    private List<ProjectState> todoList;
    private List<ProjectState> inProgressList;
    private List<ProjectState> doneList;
}
