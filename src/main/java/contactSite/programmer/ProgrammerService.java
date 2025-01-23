package contactSite.programmer;

import contactSite.Field;
import contactSite.programmer.dto.ProgrammerPasswordRequest;
import contactSite.programmer.dto.ProgrammerRequest;
import contactSite.programmer.dto.ProgrammerResponse;
import contactSite.programmer.dto.create.ProgrammerCreateRequest;
import contactSite.programmer.dto.read.ProgrammerDetailResponse;
import contactSite.programmer.dto.read.ProgrammerReadResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProgrammerService {

    private final ProgrammerRepository programmerRepository;
    private final ProgrammerQueryRepository programmerQueryRepository;

    public ProgrammerService(ProgrammerRepository programmerRepository, ProgrammerQueryRepository programmerQueryRepository) {
        this.programmerRepository = programmerRepository;
        this.programmerQueryRepository = programmerQueryRepository;
    }

    public ProgrammerResponse create(ProgrammerCreateRequest programmerRequest) {
        Programmer programmer = programmerRepository.save(new Programmer(
                programmerRequest.userId(),
                programmerRequest.password(),
                programmerRequest.name(),
                programmerRequest.age(),
                programmerRequest.email(),
                programmerRequest.personalHistory(),
                programmerRequest.fieldName(),
                programmerRequest.selfIntroduction(),
                programmerRequest.certificate()));

        return new ProgrammerResponse(
                programmer.getId(),
                programmer.getUserId(),
                programmerRequest.name(),
                programmerRequest.age(),
                programmerRequest.email(),
                programmerRequest.personalHistory(),
                programmerRequest.fieldName(),
                programmerRequest.selfIntroduction(),
                programmerRequest.certificate());
    }


    public List<ProgrammerReadResponse> findAll(List<Field> field, Integer personalHistory) {
        return programmerQueryRepository.findAll(field, personalHistory)
                .stream()
                .map(p-> new ProgrammerReadResponse(
                        p.getId(),
                        p.getName(),
                        p.getAge(),
                        p.getFieldName()
                ))
                .toList();
    }


    public ProgrammerDetailResponse findById(String programmerId) {
        Programmer programmer = programmerRepository.findById(programmerId)
                .orElseThrow(() -> new NoSuchElementException("찾으시는 개발자가 없습니다."));

        return new ProgrammerDetailResponse(
                programmer.getName(),
                programmer.getAge(),
                programmer.getEmail(),
                programmer.getPersonalHistory(),
                programmer.getFieldName(),
                programmer.getSelfIntroduction(),
                programmer.getCertificate());
    }

    public ProgrammerResponse findByMyPage(String authorization) {
        Programmer programmer = programmerRepository.findById(authorization)
                .orElseThrow(() -> new NoSuchElementException("로그인 정보가 없습니다."));

        return new ProgrammerResponse(
                programmer.getId(),
                programmer.getUserId(),
                programmer.getName(),
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

        programmer.setUserId(programmerRequest.userId());
        programmer.setName(programmerRequest.name());
        programmer.setAge(programmerRequest.age());
        programmer.setEmail(programmerRequest.email());
        programmer.setPersonalHistory(programmerRequest.personalHistory());
        programmer.setFieldName(programmerRequest.fieldName());
        programmer.setSelfIntroduction(programmerRequest.selfIntroduction());
        programmer.setCertificate(programmerRequest.certificate());


        return new ProgrammerResponse(
                programmer.getId(),
                programmer.getUserId(),
                programmer.getName(),
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
}
