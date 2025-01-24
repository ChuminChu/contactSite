package contactSite.company;

import contactSite.Field;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyQueryRepository companyQueryRepository;

    public CompanyService(CompanyRepository companyRepository, CompanyQueryRepository companyQueryRepository
    ) {
        this.companyRepository = companyRepository;
        this.companyQueryRepository = companyQueryRepository;
    }

    //회원가입 + 비밀번호 해쉬화 추가하기
    public CompanyMypageResponse create(CreateCompanyRequest request) {
        Company company = companyRepository.save(new Company(
                request.userId(),
                request.password(),   //해쉬화 추가
                request.companyname(),
                request.businesstype(),
                request.field(),
                request.website(),
                request.address(),
                request.employeeCount(),
                request.introduction(),
                request.established()));

        return new CompanyMypageResponse(
                company.getId(),
                company.getUserId(),
                company.getCompanyname(),
                company.getBusinessType(),
                company.getField(),
                company.getWebsite(),
                company.getAddress(),
                company.getEmployeeCount(),
                company.getIntroduction(),
                company.getEstablished());
    }


    // 간단 조회 (목록 조회)
    public List<CompanyResponse> findAll(List<Field> field, String address) {
        return companyQueryRepository.findAll(field, address)
                .stream()
                .map(c -> new CompanyResponse(
                        c.getId(),
                        c.getCompanyname(),
                        c.getField(),
                        c.getAddress(),
                        c.getLikeCount()
                        )).toList();
    }


    //상세조회 - 기업 findbyid
    public CompanyDetailResponse findById(String id){
        Company company = companyRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("기업을 찾을 수 없습니다"));

        return new CompanyDetailResponse(
                company.getId(),
                company.getCompanyname(),
                company.getBusinessType(),
                company.getField(),
                company.getWebsite(),
                company.getAddress(),
                company.getEmployeeCount(),
                company.getIntroduction(),
                company.getEstablished(),
                company.getLikeCount()
        );
    }


    //상세조회 - 내정보(토큰)
    public CompanyMypageResponse findMypage(String id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("로그인 정보가 없습니다"));

        return new CompanyMypageResponse(
                company.getId(),
                company.getUserId(),
                company.getCompanyname(),
                company.getBusinessType(),
                company.getField(),
                company.getWebsite(),
                company.getAddress(),
                company.getEmployeeCount(),
                company.getIntroduction(),
                company.getEstablished()
                );
    }

    @Transactional
    //수정(회원인지 확인 후 수정) - 비밀번호 제외
    public CompanyMypageResponse update(CompanyMypageRequest request, String id){
        Company companyupdate = companyRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("회원 정보 찾을 수 없음"));

        companyupdate.update(request.companyname(),
                request.businesstype(),
                request.field(),
                request.website(),
                request.address(),
                request.employeeCount(),
                request.introduction(),
                request.established());

        return new CompanyMypageResponse(
                companyupdate.getId(),
                companyupdate.getUserId(),
                companyupdate.getCompanyname(),
                companyupdate.getBusinessType(),
                companyupdate.getField(),
                companyupdate.getWebsite(),
                companyupdate.getAddress(),
                companyupdate.getEmployeeCount(),
                companyupdate.getIntroduction(),
                companyupdate.getEstablished()
        );

    }

    @Transactional
    //비밀번호 수정(로그인한 회원과 같은지 확인)
    public void updatePassword(CompanyPasswordRequest request, String id){
        Company passwordupdate = companyRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("회원정보 찾을 수 없음"));

        passwordupdate.setPassword(request.password());
    }

    //회원 탈퇴
    public void delete(String id){
        companyRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("회원 정보 찾을 수 없음"));

        companyRepository.deleteById(id);
    }

}
