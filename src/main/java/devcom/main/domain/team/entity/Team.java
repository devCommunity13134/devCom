package devcom.main.domain.team.entity;

import devcom.main.domain.project.entity.Project;
import devcom.main.domain.teamMember.entity.TeamMember;
import devcom.main.global.jpa.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
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
public class Team extends BaseEntity {

    @NotEmpty(message = "팀 명은 필수 입니다.")
    private String name;

    @NotEmpty(message = "팀 설명은 필수 입니다.")
    private String description;

    @OneToMany(mappedBy = "team", cascade = CascadeType.REMOVE)
    private List<TeamMember> teamMemberList;

    @OneToMany(mappedBy = "team", cascade = CascadeType.REMOVE)
    private List<Project> projectList;

}
