package devcom.main.domain.teamInvite.repository;

import devcom.main.domain.team.entity.Team;
import devcom.main.domain.teamInvite.entity.TeamInvite;
import devcom.main.domain.teamMember.entity.TeamMember;
import devcom.main.domain.user.entity.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamInviteRepository extends JpaRepository<TeamInvite, Long> {
    Optional<TeamInvite> findByTeamAndSiteUser(Team team, SiteUser invitedUser);
}
