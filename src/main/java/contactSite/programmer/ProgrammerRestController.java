package contactSite.programmer;

import contactSite.Field;
import contactSite.LoginUtils.LoginMember;
import contactSite.programmer.dto.PageResponse;
import contactSite.programmer.dto.ProgrammerPasswordRequest;
import contactSite.programmer.dto.ProgrammerRequest;
import contactSite.programmer.dto.create.ProgrammerCreateRequest;
import contactSite.programmer.dto.ProgrammerResponse;
import contactSite.programmer.dto.read.ProgrammerDetailResponse;
import contactSite.programmer.dto.read.ProgrammerReadResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProgrammerRestController {

    private final ProgrammerService programmerService;

    public ProgrammerRestController(ProgrammerService programmerService) {
        this.programmerService = programmerService;
    }

    //생성
    @PostMapping("/programmers")
    public ProgrammerResponse create(@RequestBody ProgrammerCreateRequest programmerRequest){
        return programmerService.create(programmerRequest);
    }

    //간단한 조회
    @GetMapping("/programmers")
    public PageResponse findAll(@LoginMember String authorization,
                                @RequestParam(required = false) List<Field> field,
                                @RequestParam(required = false) Integer personalHistory,
                                @RequestParam(defaultValue = "1") int page,
                                @RequestParam(defaultValue = "30") int size){
        Pageable pageable = PageRequest.of(page-1, size);
        return programmerService.findAll(authorization,field,personalHistory,pageable);
    }

    //상세 조회
    @GetMapping("/programmers/{programmerId}")
    public ProgrammerDetailResponse findById (@LoginMember String authorization,
                                              @PathVariable String programmerId){
        return programmerService.findById(authorization, programmerId);
    }

    //상세 조회 - 내 정보
    @GetMapping("/programmers/my")
    public ProgrammerResponse findByMypage (@LoginMember String authorization){
        return programmerService.findByMyPage(authorization);
    }

    //내 정보 수정
    @PutMapping("/programmers/my")
    public ProgrammerResponse updateMypage (@RequestBody ProgrammerRequest programmerRequest,
                                            @LoginMember String authorization){
        return programmerService.updateMyPage(programmerRequest, authorization);
    }

    @PatchMapping("/programmers/my")
    public void updatePassword(@LoginMember String authorization,
                               @RequestBody ProgrammerPasswordRequest programmerPasswordRequest){
        programmerService.updatePassword(authorization, programmerPasswordRequest);
    }

    //내 정보 삭제
    @DeleteMapping("/programmers/my")
    public void deleteMypage(@LoginMember String authorization){
        programmerService.deleteById(authorization);
    }
}
