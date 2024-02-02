package devcom.main.domain.team.entity;

import devcom.main.domain.project.entity.Project;
import devcom.main.domain.teamInvite.entity.TeamInvite;
import devcom.main.domain.teamMember.entity.TeamMember;
import devcom.main.domain.user.entity.SiteUser;
import devcom.main.global.jpa.BaseEntity;
import groovy.lang.Lazy;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Team extends BaseEntity {

    @NotEmpty
    private String name;

    @NotEmpty
    @Column(columnDefinition="text")
    private String description;

    @ManyToOne
    private SiteUser teamAdmin;

    @OneToMany(mappedBy = "team", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<TeamMember> teamMemberList;

    @OneToMany(mappedBy = "team", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Project> projectList;

    @OneToMany(mappedBy = "team", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<TeamInvite> teamInviteList;
}
