package devcom.main.domain.follow.repository;


import devcom.main.domain.follow.entity.Follower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FollowerRepository extends JpaRepository<Follower, Long> {
    Optional<Follower> findByFollowerUserId(Long id);
}
