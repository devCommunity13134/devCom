package devcom.main.domain.message.repository;

import devcom.main.domain.message.entity.ReceiveMessage;
import devcom.main.domain.message.entity.SendMessage;
import devcom.main.domain.user.entity.SiteUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReceiveMessageRepository extends JpaRepository<ReceiveMessage,Long> {

    Page<ReceiveMessage> findAllByUser(SiteUser user, Pageable pageable);
}
