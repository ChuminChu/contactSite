package contactSite.like;

import contactSite.company.Company;
import contactSite.company.CompanyRepository;
import contactSite.programmer.Programmer;
import contactSite.programmer.ProgrammerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final ProgrammerRepository programmerRepository;
    private final CompanyRepository companyRepository;

    public LikeService(LikeRepository likeRepository, ProgrammerRepository programmerRepository, CompanyRepository companyRepository) {
        this.likeRepository = likeRepository;
        this.programmerRepository = programmerRepository;
        this.companyRepository = companyRepository;
    }

    @Transactional
    public void create(String senderId, LikeRequest request) {

        // 기업이 기업에게 좋아요를 누를 경우 에러 발생
        if (isCompanyToCompany(senderId, request.receiverId())) {
            throw new IllegalArgumentException("기업이 기업에 좋아요를 누를 수 없습니다.");
        }
        if (request.receiverId().startsWith("C")) {
            Company company = companyRepository.findById(request.receiverId()).orElseThrow(
                    () -> new NoSuchElementException("해당 ID의 기업이 존재하지 않습니다!"));
            company.increaseLikeCount();
        } else if (request.receiverId().startsWith("P")) {
            Programmer programmer = programmerRepository.findById(request.receiverId()).orElseThrow(
                    () -> new NoSuchElementException("해당 ID의 개발자가 존재하지 않습니다!"));
            programmer.likeCount();
        }
        Like like = new Like(senderId, request.receiverId());
        likeRepository.save(like);
    }

    public List<LikeResponse> findAllLikes(String senderId) {

        List<Like> likeList = likeRepository.findAllBySenderId(senderId);
        List<LikeResponse> likeResponseList = new ArrayList<>();
        for (Like like : likeList) {
            if (like.getReceiverId().startsWith("C")) {
                Company company = companyRepository.findById(like.getReceiverId()).orElseThrow(
                        () -> new NoSuchElementException("해당 ID의 기업이 존재하지 않습니다!"));
                likeResponseList.add(new LikeResponse(like.getId(), company.getCompanyname()));
            } else if (like.getReceiverId().startsWith("P")) {
                Programmer programmer = programmerRepository.findById(like.getReceiverId()).orElseThrow(
                        () -> new NoSuchElementException("해당 ID의 개발자가 존재하지 않습니다!"));
                likeResponseList.add(new LikeResponse(like.getId(), programmer.getName()));
            }
        }

        return likeResponseList;
    }

    public void deleteLike(Long id, String senderId) {

        Like like = likeRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("해당 ID의 좋아요가 존재하지 않습니다!"));

        if (!like.getSenderId().equals(senderId)) throw new IllegalArgumentException("송신 ID가 일치하지 않습니다.");
        else {
            likeRepository.deleteById(id);
        }
    }

    private boolean isCompanyToCompany(String senderId, String receiverId) {

        return senderId.startsWith("C") && receiverId.startsWith("C");
    }
}
