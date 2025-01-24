package contactSite.message;

import contactSite.like.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllBySenderId(String id);
    List<Message> findAllByReceiverId(String id);
}
