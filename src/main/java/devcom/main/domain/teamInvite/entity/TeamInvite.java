package devcom.main.domain.teamInvite.entity;

import devcom.main.domain.team.entity.Team;
import devcom.main.domain.user.entity.SiteUser;
import devcom.main.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class TeamInvite extends BaseEntity {

    @ManyToOne
    private Team team;

    @ManyToOne
    private SiteUser siteUser;

    private LocalDateTime expireDate;
}
