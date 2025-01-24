package contactSite.login;

import contactSite.Field;
import contactSite.Login.LoginRequest;
import contactSite.LoginUtils.AccessToken;
import contactSite.LoginUtils.JwtProvider;
import contactSite.company.CompanyMypageResponse;
import contactSite.company.CreateCompanyRequest;
import contactSite.programmer.dto.ProgrammerResponse;
import contactSite.programmer.dto.create.ProgrammerCreateRequest;
import contactSite.utils.DatabaseCleanup;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("LoginTest")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginTest {

    @LocalServerPort
    int port;

    @Autowired
    DatabaseCleanup databaseCleanup;

    @Autowired
    JwtProvider jwtProvider;

    @BeforeEach
    void setUp() {
//        databaseCleanup.execute();
        RestAssured.port = port;
    }

    @Test
    @DisplayName("개발자 로그인 성공 테스트")
    void ProgrammerLoginSuccessTest() {

        ProgrammerResponse 개발자 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new ProgrammerCreateRequest(
                        "userId",
                        "abc123!",
                        "chu",
                        24,
                        "email",
                        1,
                        Field.Back_End,
                        "안녕하세요",
                        "없음"))
                .when()
                .post("/programmers")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(ProgrammerResponse.class);

        AccessToken token = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new LoginRequest(
                        "userId",
                        "abc123!"))
                .when()
                .post("/login/programmer")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(AccessToken.class);

        assertThat(jwtProvider.isValidToken(token.token())).isTrue();
    }

    @Test
    @DisplayName("기업 로그인 성공 테스트")
    void companyLoginSuccessTest() {

        CompanyMypageResponse 기업 = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new CreateCompanyRequest(
                        "userid",
                        "1234",
                        "name",
                        "업종",
                        Field.Back_End,
                        "웹사이트주소",
                        "지역명",
                        100,
                        "기업 소개글",
                        1995))
                .when()
                .post("/companies") // POST
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(CompanyMypageResponse.class);

        AccessToken token = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new LoginRequest(
                        "userid",
                        "1234"))
                .when()
                .post("/login/company")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(AccessToken.class);

    }


}
