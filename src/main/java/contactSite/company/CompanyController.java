package contactSite.company;

import contactSite.LoginUtils.LoginMember;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CompanyController {

    private final CompanyRepository companyRepository;
    private final CompanyService companyService;


    public CompanyController(CompanyRepository companyRepository, CompanyService companyService) {
        this.companyRepository = companyRepository;
        this.companyService = companyService;
    }


    //회원가입
    @PostMapping
    public void create(@Valid @RequestBody CreateCompanyRequest request){
        companyService.create(request);
    }

    //목록 조회
    @GetMapping
    public List<CompanyResponse> findAll(){
        return companyService.findAll();
    }

    //상세조회
    @GetMapping
    public CompanyDetailResponse detail(@LoginMember String authorization){
        return companyService.findDetail(authorization);
    }

    //회원정보수정
    @PutMapping
    public Company update(@RequestBody CreateCompanyRequest request, @LoginMember String authorization){
        return companyService.update(request,authorization);
    }

    //비밀번호 수정
    public Company password(@RequestBody CreateCompanyRequest request, @LoginMember String authorization ){
        return companyService.PWupdate(request, authorization);
    }

    //회원 탈퇴
    public void delete(@LoginMember String authorization){
        companyService.delete(authorization);
    }


}
