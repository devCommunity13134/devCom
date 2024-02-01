package devcom.main.domain.message.repository;

import devcom.main.domain.message.entity.SendMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SendMessageRepository extends JpaRepository<SendMessage, Long> {
}
