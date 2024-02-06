package devcom.main.domain.follow.repository;


import devcom.main.domain.follow.entity.Following;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FollowingRepository extends JpaRepository<Following, Long> {
    Optional<Following> findByFollowingUserId(Long id);
}
