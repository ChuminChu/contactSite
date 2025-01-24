package contactSite.companyTest;

import contactSite.Field;
import contactSite.Login.LoginRequest;
import contactSite.LoginUtils.AccessToken;
import contactSite.LoginUtils.JwtProvider;
import contactSite.company.*;
import contactSite.utils.DatabaseCleanup;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class companyApiTest {

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
    void 회원가입() {
        CompanyMypageResponse 기업 = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new CreateCompanyRequest(
                        "userid1234",
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

        assertThat(기업).isNotNull();
    }
//
////    @Test
////    void 목록조회() {
////        RestAssured
////                .given().log().all()
////                .queryParam("isSale", "true")
////                .queryParam("minPrice", "50000")
////                .queryParam("maxPrice", "100000")
////                .when()
////                .get("/products") // 서버로 GET /products 요청
////                .then().log().all()
////                .statusCode(200); // 요청에 대한 서버 응답의 상태코드가 200인지 검증
////
////    }
//
//
    @Test
    void 상세조회_기업() {
        CompanyMypageResponse 기업 = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new CreateCompanyRequest(
                        "userid1234",
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

        assertThat(기업).isNotNull();


        CompanyDetailResponse 기업상세조회 = RestAssured
                .given().log().all()
                .pathParam("companyId", 기업.id())
                .when()
                .get("/companies/{companyId}") // 서버로 GET /products 요청
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(CompanyDetailResponse.class); // 요청에 대한 서버 응답의 상태코드가 200인지 검증

        assertThat(기업상세조회.name()).isNotNull();
        assertThat(기업상세조회.businesstype()).isNotNull();
        assertThat(기업상세조회.field()).isNotNull();
        assertThat(기업상세조회.website()).isNotNull();
        assertThat(기업상세조회.address()).isNotNull();
        assertThat(기업상세조회.employeecount()).isNotNull();
        assertThat(기업상세조회.introduction()).isNotNull();
        assertThat(기업상세조회.established()).isNotNull();
        assertThat(기업상세조회.likeCount()).isNotNull();

    }

    @Test
    void 상세조회_내정보() {
        CompanyMypageResponse 기업 = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new CreateCompanyRequest(
                        "userid1234",
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

        assertThat(기업).isNotNull();
        //로그인
        AccessToken token = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new LoginRequest(
                        "userid1234",
                        "abcDEF!123456"))
                .when()
                .post("/login/company") // POST /members 요청
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(AccessToken.class);


        CompanyMypageResponse 내정보조회 = RestAssured
                .given().log().all()
                // TODO: "token" 실제 코드 작성
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token.token())
                .when()
                .get("/companies/my")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(CompanyMypageResponse.class);
    }

    @Test
    void 회원정보_수정() {

        CompanyMypageResponse 기업 = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new CreateCompanyRequest(
                        "userid1234",
                        "abcDEF!123456",
                        "수정전이름",
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

        assertThat(기업).isNotNull();

        //로그인
        AccessToken token = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new LoginRequest(
                        "userid1234",
                        "abcDEF!123456"))
                .when()
                .post("/login/company") // POST /members 요청
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(AccessToken.class);

        //수정후
        CompanyMypageResponse 내정보수정 = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token.token())
                .body(new CompanyMypageRequest(
                        "userid1234",
                        "수정후이름",
                        "업종",
                        Field.Back_End,
                        "https://www.kakaocorp.com/page/",
                        "지역명",
                        100,
                        "기업 소개글",
                        LocalDate.parse("2024-05-05")))
                .when()
                .put("/companies/my") // POST
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(CompanyMypageResponse.class);

        assertThat(기업.companyname()).isEqualTo("수정전이름");
        assertThat(내정보수정.companyname()).isEqualTo("수정후이름");
        assertThat(!기업.companyname().equals(내정보수정.companyname())).isTrue();
    }

    @Test
    void 비밀번호수정() {
        CompanyMypageResponse 기업 = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new CreateCompanyRequest(
                        "userid1234",
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

        assertThat(기업).isNotNull();
        //로그인
        AccessToken token = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new LoginRequest(
                        "userid1234",
                        "abcDEF!123456"))
                .when()
                .post("/login/company") // POST /members 요청
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(AccessToken.class);

        //수정
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                // TODO: "token" 실제 코드 작성
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token.token())
                .body(new CompanyPasswordRequest("ABCdef!!1234"))
                .when()
                .patch("/companies/my") // POST /members 요청
                .then().log().all()
                .statusCode(200);
    }

    @Test
    void 회원삭제() {
        CompanyMypageResponse 기업 = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new CreateCompanyRequest(
                        "userid1234",
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

        assertThat(기업).isNotNull();

        //로그인
        AccessToken token = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new LoginRequest(
                        "userid1234",
                        "abcDEF!123456"))
                .when()
                .post("/login/company") // POST /members 요청
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(AccessToken.class);


        RestAssured
                .given().log().all()
                // TODO: "token" 실제 코드 작성
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token.token())
                .when()
                .delete("/companies/my")
                .then().log().all()
                .statusCode(200);

    }
}

