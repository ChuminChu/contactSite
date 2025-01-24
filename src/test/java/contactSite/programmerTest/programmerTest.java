package contactSite.programmerTest;

import contactSite.Field;
import contactSite.Login.LoginRequest;
import contactSite.LoginUtils.AccessToken;
import contactSite.LoginUtils.JwtProvider;
import contactSite.programmer.dto.ProgrammerPasswordRequest;
import contactSite.programmer.dto.ProgrammerRequest;
import contactSite.programmer.dto.ProgrammerResponse;
import contactSite.programmer.dto.create.ProgrammerCreateRequest;
import contactSite.programmer.dto.read.ProgrammerDetailResponse;
import contactSite.utils.DatabaseCleanup;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class programmerTest {

    @LocalServerPort
    int port;

    @Autowired
    DatabaseCleanup databaseCleanup;

    @Autowired
    JwtProvider jwtProvider;

    @BeforeEach
    void setUp() {
        databaseCleanup.execute();
        RestAssured.port = port;
    }

    @Test
    void 개발자생성() {
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

        assertThat(개발자).isNotNull();
    }
    @Test
    void 개발자_상세조회() {
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

        //로그인
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

        ProgrammerDetailResponse 개발자상세 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token.token())
                .pathParam("programmerId", 개발자.id())
                .when()
                .get("/programmers/{programmerId}")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(ProgrammerDetailResponse.class);

        assertThat(개발자상세.name()).isNotNull();
        assertThat(개발자상세.age()).isNotNull();
        assertThat(개발자상세.email()).isNotNull();
        assertThat(개발자상세.personalHistory()).isNotNull();
        assertThat(개발자상세.fieldName()).isNotNull();
        assertThat(개발자상세.selfIntroduction()).isNotNull();
        assertThat(개발자상세.certificate()).isNotNull();
    }

    @Test
    void 개발자_내정보_상세조회() {
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

        //로그인
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

        ProgrammerResponse 개발자상세 = RestAssured
                .given().log().all()
                // TODO: "token" 실제 코드 작성
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token.token())
                .when()
                .get("/programmers/my")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(ProgrammerResponse.class);
    }

    @Test
    void 개발자_수정() {
        String 수정전이름 = "수정 전 이름";
        String 수정후이름 = "수정 후 이름";
        ProgrammerResponse 개발자 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new ProgrammerCreateRequest(
                        "userId",
                        "abc123!",
                        수정전이름,
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

        //로그인
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

        ProgrammerResponse 개발자수정 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                // TODO: "token" 실제 코드 작성
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token.token())
                .body(new ProgrammerRequest(
                        "userId",
                        수정후이름,
                        24,
                        "chu@gmail",
                        1,
                        Field.Back_End,
                        "안뇽",
                        "없음"))
                .when()
                .put("/programmers/my")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(ProgrammerResponse.class);

        assertThat(개발자.name()).isEqualTo(수정전이름);
        assertThat(개발자수정.name()).isEqualTo(수정후이름);
        assertThat(!개발자.name().equals(개발자수정.name())).isTrue();
    }

    @Test
    void 비밀번호_수정() {
        String 수정전비번 = "수정 전 비번";
        String 수정후비번 = "수정 후 비번";
        ProgrammerResponse 개발자 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new ProgrammerCreateRequest(
                        "userId",
                        수정전비번,
                        "추민영",
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

        //로그인
        AccessToken token = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new LoginRequest(
                        "userId",
                        수정전비번))
                .when()
                .post("/login/programmer")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(AccessToken.class);

        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                // TODO: "token" 실제 코드 작성
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token.token())
                .body(new ProgrammerPasswordRequest(
                        수정후비번
                ))
                .when()
                .patch("/programmers/my")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    void 개발자_삭제() {
        ProgrammerResponse 개발자 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new ProgrammerCreateRequest(
                        "userId",
                        "abc123!",
                        "추민영",
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

        //로그인
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

        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                // TODO: "token" 실제 코드 작성
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token.token())
                .when()
                .delete("/programmers/my")
                .then().log().all()
                .statusCode(200);
    }
}
