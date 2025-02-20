package contactSite.company;

import contactSite.Field;
import contactSite.like.LikeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyQueryRepository companyQueryRepository;
    private final LikeRepository likeRepository;

    public CompanyService(CompanyRepository companyRepository, CompanyQueryRepository companyQueryRepository, LikeRepository likeRepository
    ) {
        this.companyRepository = companyRepository;
        this.companyQueryRepository = companyQueryRepository;
        this.likeRepository = likeRepository;
    }

    //회원가입 + 비밀번호 해쉬화
    public CompanyMypageResponse create(CreateCompanyRequest request) {
        Company company = companyRepository.save(new Company(
                request.userId(),
                request.password(),
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
    public PageResponse findAll(List<Field> field, String address, int page, int size) {
        List<CompanyResponse> list = companyQueryRepository.findAll(field, address, page, size)
                .stream()
                .map(c -> new CompanyResponse(
                        c.getId(),
                        c.getCompanyname(),
                        c.getField(),
                        c.getAddress(),
                        c.getLikeCount()
                )).toList();
        long count = companyQueryRepository.count();

        long totalpagecount = (count + size - 1) / size;

        return new PageResponse(totalpagecount,page,list);
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

    private Boolean isLiked(String senderId, String receiverId) {
        return likeRepository.findBySenderIdAndReceiverId(senderId, receiverId) != null;
    }

}
