package contactSite.like;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findAllBySenderId(String id);

    Like findBySenderIdAndReceiverId(String senderId, String receiverId);
}
