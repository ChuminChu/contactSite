package contactSite.company;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    //회원가입 + 비밀번호 해쉬화 추가하기
    public void create(CreateCompanyRequest request) {
        companyRepository.save(new Company(
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
    }


    //목록 조회(id, 이름, 분야, 위치) - 정렬 추가하기
    public List<CompanyResponse> findAll() {
        List<Company> companies = companyRepository.findAll();
        return companies.stream()
                .sorted(Comparator
                        .comparingInt(Company ::getLikeCount).reversed()    //좋아요 개수로 내림차순
                        .thenComparing(Company :: getField, Comparator.reverseOrder())
                )
                .map(company -> new CompanyResponse(
                                company.getId(),
                                company.getCompanyname(),
                                company.getField(),
                                company.getAddress()))
                .toList();
    }

    //userid


    //내정보 - 좋아요 개수도?
    public CompanyDetailResponse findDetail(String id) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 기업"));

        return new CompanyDetailResponse(
                company.getId(),
                company.getUserId(),
                company.getPassword(),
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

    @Transactional
    //수정(회원인지 확인 후 수정)
    public Company update(CreateCompanyRequest request, String id){
        Company companyupdate = companyRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("존재하지 않는 기업"));

        companyupdate.setCompanyname(request.companyname());
        companyupdate.setBusinessType(request.businesstype());
        companyupdate.setField(request.field());
        companyupdate.setWebsite(request.website());
        companyupdate.setAddress(request.address());
        companyupdate.setEmployeeCount(request.employeeCount());
        companyupdate.setIntroduction(request.introduction());
        companyupdate.setEstablished(request.established());

        return companyupdate;
    };

    @Transactional
    //비밀번호 수정(로그인한 회원과 같은지 확인)
    public Company PWupdate(CreateCompanyRequest request, String id){
        Company passwordupdate = companyRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("회원정보 찾을 수 없음"));

        passwordupdate.setPassword(request.password());
        return passwordupdate;
    }

    //회원 탈퇴
    public void delete(String id){
        companyRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("회원 정보 찾을 수 없음"));
    }

}
