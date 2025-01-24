package contactSite.company;

import contactSite.Field;
import contactSite.LoginUtils.LoginMember;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CompanyController {

    private final CompanyService companyService;


    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }


    //회원가입
    @PostMapping("/companies")
    public CompanyMypageResponse create(@Valid @RequestBody CreateCompanyRequest request){
       return companyService.create(request);
    }


    //간단 조회(목록조회)
    @GetMapping("/companies")
    public List<CompanyResponse> findAll(@RequestParam List<Field> field,
                                         @RequestParam String address){
        return companyService.findAll(field,address);
    }

    //상세조회 - 기업
    @GetMapping("/companies/{companyId}")
    public CompanyDetailResponse findByCompany(@PathVariable String companyId){
        return companyService.findById(companyId);
    }


    //상세조회 - 내 정보
    @GetMapping("/companies/my")
    public CompanyMypageResponse detail(@LoginMember String authorization){
        return companyService.findMypage(authorization);
    }

    //회원정보 수정
    @PutMapping("/companies/my")
    public CompanyMypageResponse update(@RequestBody CompanyMypageRequest request,
                          @LoginMember String authorization){
        return companyService.update(request,authorization);
    }

    //비밀번호 수정
    @PatchMapping("/companies/my")
    public void password(@RequestBody CompanyPasswordRequest request,
                            @LoginMember String authorization ){
        companyService.updatePassword(request, authorization);
    }

    //회원 탈퇴
    @DeleteMapping("/companies/my")
    public void delete(@LoginMember String authorization){
        companyService.delete(authorization);
    }


}
