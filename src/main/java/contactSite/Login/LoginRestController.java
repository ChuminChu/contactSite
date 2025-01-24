package contactSite.Login;

import contactSite.LoginUtils.AccessToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginRestController {

    private final LoginService loginService;

    public LoginRestController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login/programmer")
    public AccessToken loginProgrammer(@RequestBody LoginRequest request) {

        return loginService.loginProgrammer(request);
    }

    @PostMapping("/login/company")
    public AccessToken loginCompany(@RequestBody LoginRequest request) {

        return loginService.loginCompany(request);
    }
}
