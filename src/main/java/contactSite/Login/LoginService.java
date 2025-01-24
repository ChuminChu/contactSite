package contactSite.Login;

import contactSite.LoginUtils.AccessToken;
import contactSite.LoginUtils.JwtProvider;
import contactSite.company.Company;
import contactSite.company.CompanyRepository;
import contactSite.programmer.Programmer;
import contactSite.programmer.ProgrammerRepository;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final ProgrammerRepository programmerRepository;
    private final CompanyRepository companyRepository;
    private final JwtProvider jwtProvider;

    public LoginService(ProgrammerRepository programmerRepository, CompanyRepository companyRepository, JwtProvider jwtProvider) {
        this.programmerRepository = programmerRepository;
        this.companyRepository = companyRepository;
        this.jwtProvider = jwtProvider;
    }

    public AccessToken loginProgrammer(LoginRequest request) {
        Programmer programmer = programmerRepository.findByUserId(request.userId());
        if (programmer == null || !programmer.isCorrectPassword(request.password())) {
            throw new IllegalArgumentException("등록되지 않은 아이디 / 비밀번호 오류");
        }
        else {
            return new AccessToken(jwtProvider.createToken(programmer.getId()));
        }
    }

    public AccessToken loginCompany(LoginRequest request) {
        Company company = companyRepository.findByUserId(request.userId());
        if (company == null || !company.isCorrectPassword(request.password())) {
            throw new IllegalArgumentException("등록되지 않은 아이디 / 비밀번호 오류");
        }
        else {
            return new AccessToken(jwtProvider.createToken(company.getId()));
        }
    }
}
