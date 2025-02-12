package contactSite.likeTest;

import contactSite.Field;
import contactSite.Login.LoginRequest;
import contactSite.LoginUtils.AccessToken;
import contactSite.LoginUtils.JwtProvider;
import contactSite.company.CompanyMypageResponse;
import contactSite.company.CompanyRepository;
import contactSite.company.CreateCompanyRequest;
import contactSite.like.LikeRequest;
import contactSite.like.LikeResponse;
import contactSite.programmer.Programmer;
import contactSite.programmer.ProgrammerRepository;
import contactSite.programmer.dto.ProgrammerResponse;
import contactSite.programmer.dto.create.ProgrammerCreateRequest;
import contactSite.utils.DatabaseCleanup;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class likeTest {

    @LocalServerPort
    int port;

    @Autowired
    DatabaseCleanup databaseCleanup;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    ProgrammerRepository programmerRepository;
    @Autowired
    CompanyRepository companyRepository;

    @BeforeEach
    void setUp() {
        databaseCleanup.execute();
        RestAssured.port = port;
    }

    @Test
    @DisplayName("좋아요 생성 테스트")
    void createLikeTest() {
        ProgrammerResponse 개발자1 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new ProgrammerCreateRequest(
                        "userId1234",
                        "abcDEF123!",
                        "a",
                        LocalDate.parse("2001-01-01"),
                        "emailtest@gmail.com",
                        1,
                        List.of(Field.Back_End,Field.Front_End),
                        "안녕하세요",
                        "없음"))
                .when()
                .post("/programmers")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(ProgrammerResponse.class);

        ProgrammerResponse 개발자2 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new ProgrammerCreateRequest(
                        "userId12345",
                        "abcDEF123!",
                        "b",
                        LocalDate.parse("2002-02-02"),
                        "emailtest2@gmail.com",
                        1,
                        List.of(Field.Back_End,Field.Front_End),
                        "안녕하세요",
                        "없음"))
                .when()
                .post("/programmers")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(ProgrammerResponse.class);

        // 개발자1 아이디로 로그인
        AccessToken token = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new LoginRequest(
                        "userId1234",
                        "abcDEF123!"))
                .when()
                .post("/login/programmer")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(AccessToken.class);

        RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token.token())
                .contentType(ContentType.JSON)
                .body(new LikeRequest(
                        개발자2.id()))
                .when()
                .post("/likes") // POST
                .then().log().all()
                .statusCode(200);

        Programmer p2 = programmerRepository.findById(개발자2.id()).orElseThrow();
        assertThat(p2.getLikeCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("사용자의 모든 좋아요 조회 테스트")
    void readAllLikesTest() {
        ProgrammerResponse 개발자1 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new ProgrammerCreateRequest(
                        "userId1234",
                        "abcDEF123!",
                        "a",
                        LocalDate.parse("2001-01-01"),
                        "emailtest@gmail.com",
                        1,
                        List.of(Field.Back_End,Field.Front_End),
                        "안녕하세요",
                        "없음"))
                .when()
                .post("/programmers")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(ProgrammerResponse.class);

        ProgrammerResponse 개발자2 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new ProgrammerCreateRequest(
                        "userId12345",
                        "abcDEF123!",
                        "b",
                        LocalDate.parse("2002-02-02"),
                        "emailtest2@gmail.com",
                        1,
                        List.of(Field.Back_End,Field.Front_End),
                        "안녕하세요",
                        "없음"))
                .when()
                .post("/programmers")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(ProgrammerResponse.class);

        CompanyMypageResponse 기업1 = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new CreateCompanyRequest(
                        "userid123456",
                        "abcDEF!123456",
                        "이름",
                        "업종",
                        Field.Back_End,
                        "https://www.kakaocorp.com/page/",
                        "지역명",
                        100,
                        "기업 소개글",
                        LocalDate.parse("2024-05-05")))
                .when()
                .post("/companies") // POST
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(CompanyMypageResponse.class);

        // 개발자1 아이디로 로그인
        AccessToken token = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new LoginRequest(
                        "userId1234",
                        "abcDEF123!"))
                .when()
                .post("/login/programmer")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(AccessToken.class);

        RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token.token())
                .contentType(ContentType.JSON)
                .body(new LikeRequest(
                        개발자2.id()))
                .when()
                .post("/likes") // POST
                .then().log().all()
                .statusCode(200);

        RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token.token())
                .contentType(ContentType.JSON)
                .body(new LikeRequest(
                        기업1.id()))
                .when()
                .post("/likes") // POST
                .then().log().all()
                .statusCode(200);

        List<LikeResponse> likeResponses = RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token.token())
                .when()
                .get("/likes")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(new TypeRef<List<LikeResponse>>() {});

        assertThat(likeResponses.get(0).receiverName()).isEqualTo("b");
        assertThat(likeResponses.get(1).receiverName()).isEqualTo("이름");
    }

}