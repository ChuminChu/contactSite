package contactSite.company;

import contactSite.Field;
import contactSite.LoginUtils.LoginMember;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class CompanyController {

    private final CompanyService companyService;


    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }


    //회원가입
    @PostMapping("/companies")
    public CompanyMypageResponse create(@Valid @RequestBody CreateCompanyRequest request) {
        return companyService.create(request);
    }


    //간단 조회(목록조회)
    @GetMapping("/companies")
    public PageResponse findAll(@RequestParam(required = false) List<Field> field,
                                @RequestParam(required = false) String address,
                                @RequestParam(defaultValue = "1") int page,
                                @RequestParam(defaultValue = "5") int size) {
        return companyService.findAll(field,address,page,size);
//        return companyService.findAll(field,address);

//        List<CompanyResponse> companies = List.of(
//                new CompanyResponse("C1", "Tech Innovators", Field.Front_End, "1234 Silicon Valley, CA", 120),
//                new CompanyResponse("C2", "Green World", Field.Front_End, "789 Green Street, NY", 150),
//                new CompanyResponse("C3", "HealthTech Solutions", Field.Front_End, "456 Wellness Ave, TX", 90),
//                new CompanyResponse("C4", "Eco Energy", Field.Back_End, "321 Clean Road, FL", 200),
//                new CompanyResponse("C5", "Global Logistics", Field.Front_End, "1125 Transport Blvd, IL", 85),
//                new CompanyResponse("C6", "Foodie Inc.", Field.Back_End, "234 Delicious St, CA", 130),
//                new CompanyResponse("C7", "Digital Futures", Field.Back_End, "987 Future Blvd, WA", 175),
//                new CompanyResponse("C8", "Smart Homes", Field.Back_End, "654 Home St, CO", 160),
//                new CompanyResponse("C9", "Fashion Empire", Field.DBManege, "123 Fashion Rd, NY", 200),
//                new CompanyResponse("C10", "Automotive Experts", Field.Front_End, "321 Motor Lane, MI", 95),
//                new CompanyResponse("C11", "Cyber Security Co.", Field.ServerMange, "876 Secure Ave, TX", 180),
//                new CompanyResponse("C12", "CleanTech Solutions", Field.ServerMange, "543 Eco Dr, FL", 110),
//                new CompanyResponse("C13", "HealthPlus", Field.ServerMange, "678 Healthy Blvd, NV", 140),
//                new CompanyResponse("C14", "Mobile Innovations", Field.ServerMange, "432 Mobile Way, CA", 155),
//                new CompanyResponse("C15", "Luxury Living", Field.DBManege, "345 Elite St, NY", 120),
//                new CompanyResponse("C16", "AutoDrive", Field.DBManege, "543 Auto Dr, MI", 105),
//                new CompanyResponse("C17", "Fintech Global", Field.DBManege, "210 Money Ave, NY", 180),
//                new CompanyResponse("C18", "Outdoor Adventures", Field.Full_Stack, "753 Adventure Blvd, CO", 110),
//                new CompanyResponse("C19", "Future Foods", Field.Full_Stack, "876 Veggie Lane, CA", 140),
//                new CompanyResponse("C20", "Space Innovations", Field.Full_Stack, "999 Rocket Rd, TX", 190)
//        );

//        if (field != null && field.size() > 0) {
//            return companies.stream()
//                    .filter(c -> field.contains(c.field()))
//                    .toList()
//                    .subList((page - 1) * size, page * size);
//        }
//        return companies.subList((page - 1) * size, page * size);
    }

    //상세조회 - 기업
    @GetMapping("/companies/{companyId}")
    public CompanyDetailResponse findByCompany(@PathVariable String companyId) {
//        return companyService.findById(companyId);

        //프론트테스트용
        return new CompanyDetailResponse("C1", "Space Innovations",
                "it", Field.Front_End, "https://www.kakaocorp.com/page/service/service/KakaoTalk",
                "주소", 100, "회사소개", LocalDate.parse("2011-11-11"), 190);
    }


    //상세조회 - 내 정보
    @GetMapping("/companies/my")
    public CompanyMypageResponse detail(@LoginMember String authorization) {
        return companyService.findMypage(authorization);
    }

    //회원정보 수정
    @PutMapping("/companies/my")
    public CompanyMypageResponse update(@Valid @RequestBody CompanyMypageRequest request,
                                        @LoginMember String authorization) {
        return companyService.update(request, authorization);
    }

    //비밀번호 수정
    @PatchMapping("/companies/my")
    public void password(@Valid @RequestBody CompanyPasswordRequest request,
                         @LoginMember String authorization) {
        companyService.updatePassword(request, authorization);
    }

    //회원 탈퇴
    @DeleteMapping("/companies/my")
    public void delete(@LoginMember String authorization) {
        companyService.delete(authorization);
    }


}
