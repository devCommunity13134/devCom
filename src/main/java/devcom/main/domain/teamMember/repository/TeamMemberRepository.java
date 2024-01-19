package devcom.main.domain.teamMember.repository;

import devcom.main.domain.teamMember.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
}
