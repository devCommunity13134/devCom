package devcom.main.domain.project.entity;

import devcom.main.domain.projectState.entity.ProjectState;
import devcom.main.domain.team.entity.Team;
import devcom.main.domain.teamMember.entity.TeamMember;
import devcom.main.global.jpa.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Project extends BaseEntity {

    @NotEmpty(message = "프로젝트명은 필수 입니다.")
    private String name;

    @NotEmpty(message = "프로젝트 설명은 필수 입니다.")
    private String description;

    @ManyToOne
    private Team team;

    @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
    private List<ProjectState> projectStates;
}
