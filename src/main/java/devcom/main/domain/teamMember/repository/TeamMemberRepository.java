package devcom.main.domain.teamMember.repository;

import devcom.main.domain.team.entity.Team;
import devcom.main.domain.teamMember.entity.TeamMember;
import devcom.main.domain.user.entity.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
    Optional<TeamMember> findByTeamAndSiteUser(Team team, SiteUser siteUser);
}
