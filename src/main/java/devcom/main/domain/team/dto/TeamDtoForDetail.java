package devcom.main.domain.team.dto;

import devcom.main.domain.project.entity.Project;
import devcom.main.domain.teamMember.entity.TeamMember;
import devcom.main.domain.user.entity.SiteUser;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class TeamDtoForDetail {

    private Long id;

    private String name;

    private String description;

    private String teamAdminName;

    private List<SiteUser> teamMemberList;

    private List<Project> projectList;

    private LocalDateTime createDate;
}
