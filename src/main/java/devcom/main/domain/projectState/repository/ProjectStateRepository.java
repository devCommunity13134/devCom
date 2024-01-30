package devcom.main.domain.projectState.repository;

import devcom.main.domain.projectState.entity.ProjectState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectStateRepository extends JpaRepository<ProjectState,Long> {
}
