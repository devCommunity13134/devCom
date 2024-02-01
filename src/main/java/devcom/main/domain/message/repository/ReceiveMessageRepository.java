package devcom.main.domain.message.repository;

import devcom.main.domain.message.entity.ReceiveMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReceiveMessageRepository extends JpaRepository<ReceiveMessage,Long> {
}
