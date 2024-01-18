package devcom.main.domain.teamMember.entity;

import devcom.main.domain.team.entity.Team;
import devcom.main.domain.user.entity.SiteUser;
import devcom.main.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class TeamMember extends BaseEntity {

    @OneToOne
    private SiteUser siteUser;
    private String authority;

    @ManyToOne
    private Team team;
}
