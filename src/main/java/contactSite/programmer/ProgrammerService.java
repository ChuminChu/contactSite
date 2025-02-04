package contactSite.programmer;

import contactSite.Field;
import contactSite.like.LikeRepository;
import contactSite.programmer.dto.ProgrammerPasswordRequest;
import contactSite.programmer.dto.ProgrammerRequest;
import contactSite.programmer.dto.ProgrammerResponse;
import contactSite.programmer.dto.create.ProgrammerCreateRequest;
import contactSite.programmer.dto.read.ProgrammerDetailResponse;
import contactSite.programmer.dto.read.ProgrammerReadResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProgrammerService {

    private final ProgrammerRepository programmerRepository;
    private final ProgrammerQueryRepository programmerQueryRepository;
    private final LikeRepository likeRepository;

    public ProgrammerService(ProgrammerRepository programmerRepository, ProgrammerQueryRepository programmerQueryRepository, LikeRepository likeRepository) {
        this.programmerRepository = programmerRepository;
        this.programmerQueryRepository = programmerQueryRepository;
        this.likeRepository = likeRepository;
    }

    public ProgrammerResponse create(ProgrammerCreateRequest programmerRequest) {
        Programmer programmer = programmerRepository.save(new Programmer(
                programmerRequest.userId(),
                programmerRequest.password(),
                programmerRequest.name(),
                programmerRequest.birthDate(),
                programmerRequest.email(),
                programmerRequest.personalHistory(),
                programmerRequest.fieldName(),
                programmerRequest.selfIntroduction(),
                programmerRequest.certificate()));

        programmer.countAge();

        return new ProgrammerResponse(
                programmer.getId(),
                programmer.getUserId(),
                programmerRequest.name(),
                programmerRequest.birthDate(),
                programmer.getAge(),
                programmerRequest.email(),
                programmerRequest.personalHistory(),
                programmerRequest.fieldName(),
                programmerRequest.selfIntroduction(),
                programmerRequest.certificate());
    }


    public List<ProgrammerReadResponse> findAll(String authorization, List<Field> field, Integer personalHistory) {

        List<Programmer> programmerList = programmerQueryRepository.findAll(field, personalHistory);
        List<ProgrammerReadResponse> programmerReadResponses = new ArrayList<>();

        for (Programmer p : programmerList) {
            programmerReadResponses.add(
                    new ProgrammerReadResponse(
                            p.getId(),
                            p.getName(),
                            p.getAge(),
                            p.getFieldName(),
                            isLiked(authorization, p.getId()),
                            p.getLikeCount()));

        }

        return programmerReadResponses;
    }


    public ProgrammerDetailResponse findById(String authorization, String programmerId) {
        Programmer programmer = programmerRepository.findById(programmerId)
                .orElseThrow(() -> new NoSuchElementException("찾으시는 개발자가 없습니다."));

        return new ProgrammerDetailResponse(
                programmer.getName(),
                programmer.getAge(),
                programmer.getEmail(),
                programmer.getPersonalHistory(),
                programmer.getFieldName(),
                programmer.getSelfIntroduction(),
                programmer.getCertificate(),
                isLiked(authorization, programmerId),
                programmer.getLikeCount());
    }

    public ProgrammerResponse findByMyPage(String authorization) {
        Programmer programmer = programmerRepository.findById(authorization)
                .orElseThrow(() -> new NoSuchElementException("로그인 정보가 없습니다."));

        return new ProgrammerResponse(
                programmer.getId(),
                programmer.getUserId(),
                programmer.getName(),
                programmer.getBirthDate(),
                programmer.getAge(),
                programmer.getEmail(),
                programmer.getPersonalHistory(),
                programmer.getFieldName(),
                programmer.getSelfIntroduction(),
                programmer.getCertificate());
    }

    @Transactional
    public ProgrammerResponse updateMyPage(ProgrammerRequest programmerRequest, String authorization) {
        Programmer programmer = programmerRepository.findById(authorization)
                .orElseThrow(() -> new NoSuchElementException("로그인 정보가 없습니다."));

        programmer.update(programmerRequest.userId(),
                programmerRequest.name(),
                programmerRequest.email(),
                programmerRequest.birthDate(),
                programmerRequest.personalHistory(),
                programmerRequest.fieldName(),
                programmerRequest.selfIntroduction(),
                programmerRequest.certificate());

        programmer.countAge();

        return new ProgrammerResponse(
                programmer.getId(),
                programmer.getUserId(),
                programmer.getName(),
                programmer.getBirthDate(),
                programmer.getAge(),
                programmer.getEmail(),
                programmer.getPersonalHistory(),
                programmer.getFieldName(),
                programmer.getSelfIntroduction(),
                programmer.getCertificate());
    }

    @Transactional
    public void updatePassword(String authorization, ProgrammerPasswordRequest programmerPasswordRequest) {
        Programmer programmer = programmerRepository.findById(authorization)
                .orElseThrow(() -> new NoSuchElementException("로그인 정보가 없습니다."));

        programmer.setPassword(programmerPasswordRequest.password());
    }

    public void deleteById(String authorization) {
        programmerRepository.findById(authorization)
                .orElseThrow(() -> new NoSuchElementException("로그인 정보가 없습니다."));

        programmerRepository.deleteById(authorization);
    }

    private Boolean isLiked(String senderId, String receiverId) {
        return likeRepository.findBySenderIdAndReceiverId(senderId, receiverId) != null;
    }
}
