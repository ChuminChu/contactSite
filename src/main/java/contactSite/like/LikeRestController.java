package contactSite.like;

import contactSite.LoginUtils.LoginMember;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LikeRestController {

    private final LikeService likeService;

    public LikeRestController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/likes")
    public void createLike(@LoginMember String senderId, @RequestBody LikeRequest request) {

        likeService.create(senderId, request);
    }

    // 사용자가 좋아요를 누른 전체 대상 조회
    @GetMapping("/likes")
    public List<LikeResponse> findAllLikes(@LoginMember String senderId) {

        return likeService.findAllLikes(senderId);
    }

    @DeleteMapping("/likes/{id}")
    public void deleteLike(@PathVariable Long id, @LoginMember String senderId) {

        likeService.deleteLike(id, senderId);
    }
}
