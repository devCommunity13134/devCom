package devcom.main.domain.project.repository;

import devcom.main.domain.project.entity.Project;
import devcom.main.domain.team.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Object> findByTeam(Team team);

    Optional<Project> findByTeamAndName(Team team, String name);

}
