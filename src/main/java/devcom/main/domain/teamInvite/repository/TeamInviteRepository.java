package devcom.main.domain.teamInvite.repository;

import devcom.main.domain.teamInvite.entity.TeamInvite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamInviteRepository extends JpaRepository<TeamInvite, Long> {
}
