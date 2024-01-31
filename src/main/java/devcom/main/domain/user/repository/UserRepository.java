package devcom.main.domain.user.repository;

import devcom.main.domain.user.entity.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<SiteUser,Long> {

    Optional<SiteUser> findByUsername(String username);
    Optional<SiteUser> findByNickname(String nickname);


}
