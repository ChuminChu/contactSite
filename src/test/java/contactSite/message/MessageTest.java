package contactSite.message;

import contactSite.Field;
import contactSite.Login.LoginRequest;
import contactSite.LoginUtils.AccessToken;
import contactSite.LoginUtils.JwtProvider;
import contactSite.company.CompanyMypageResponse;
import contactSite.company.CreateCompanyRequest;
import contactSite.message.dto.MessageRequest;
import contactSite.message.dto.MessageResponse;
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
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MessageTest {
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

    private String programmerId = "userId1234";
    private String programmerPassword = "abcDEF123!";

    private String companyId = "userid1234";
    private String companyPassword = "abcDEF!123456";

    private ProgrammerResponse 개발자(String programmerId, String programmerPassword){
        return RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new ProgrammerCreateRequest(
                        programmerId,
                        programmerPassword,
                        "chu",
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
    }

    private CompanyMypageResponse 기업(String companyId, String companyPassword){
        return RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new CreateCompanyRequest(
                        companyId,
                        companyPassword,
                        "기업이름",
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
    }

    private AccessToken 개발자로그인(String programmerId, String programmerPassword){
        return RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new LoginRequest(
                        programmerId,
                        programmerPassword))
                .when()
                .post("/login/programmer")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(AccessToken.class);
    }

    private AccessToken 기업로그인(String companyId, String companyPassword){
        return RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new LoginRequest(
                        companyId,
                        companyPassword))
                .when()
                .post("/login/company")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(AccessToken.class);
    }

    @Test
    @DisplayName("개발자가 기업에게 쪽지 보내는 테스트")
    void 쪽지생성1() {
        ProgrammerResponse 개발자1 = 개발자(programmerId, programmerPassword);
        CompanyMypageResponse 기업1 = 기업(companyId, companyPassword);

        AccessToken 개발자1_토큰 = 개발자로그인(programmerId, programmerPassword);

        MessageResponse 쪽지 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new MessageRequest(기업1.id()))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 개발자1_토큰.token())
                .when()
                .post("/messages")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(MessageResponse.class);

        assertThat(쪽지).isNotNull();
        assertThat(쪽지.senderOrReceiverName()).isEqualTo("기업이름");
    }

    @Test
    @DisplayName("기업이 개발자에게 쪽지 보내는 테스트")
    void 쪽지생성2() {
        ProgrammerResponse 개발자1 = 개발자(programmerId, programmerPassword);

        CompanyMypageResponse 기업1 = 기업(companyId, companyPassword);

        AccessToken 기업1_토큰 = 기업로그인(companyId, companyPassword);

        MessageResponse 쪽지 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new MessageRequest(기업1.id()))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 기업1_토큰.token())
                .when()
                .post("/messages")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(MessageResponse.class);

        assertThat(쪽지).isNotNull();
    }

    @Test
    @DisplayName("내가 보낸 쪽지(내가 개발자일때)")
    void 내가_보낸_쪽지1() {
        ProgrammerResponse 개발자1 = 개발자(programmerId, programmerPassword);

        CompanyMypageResponse 기업1 = 기업(companyId, companyPassword);

        CreateCompanyRequest newCompany = new CreateCompanyRequest(
                "companyId2",
                "companyPassword123!",
                "기업2",
                "kakao",
                Field.Front_End,
                "https://www.kakaocorp.com/page/",
                "주소",
                12,

                "한줄소개",
                LocalDate.parse("2025-01-24")
        );
        CompanyMypageResponse 기업2 = 기업(newCompany.userId(),newCompany.password());

        AccessToken 개발자1_토큰 = 개발자로그인(programmerId, programmerPassword);

        MessageResponse 쪽지1 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new MessageRequest(기업1.id()))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 개발자1_토큰.token())
                .when()
                .post("/messages")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(MessageResponse.class);

        MessageResponse 쪽지2 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new MessageRequest(기업2.id()))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 개발자1_토큰.token())
                .when()
                .post("/messages")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(MessageResponse.class);

        List<MessageResponse> 쪽지들 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 개발자1_토큰.token())
                .when()
                .get("/messages/senders")
                .then().log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList(".", MessageResponse.class);

        assertThat(쪽지들).hasSize(2);
        assertThat(쪽지들.get(0).senderOrReceiverName()).isEqualTo("기업이름");


    }

    @Test
    @DisplayName("내가 보낸 쪽지(내가 기업일때)")
    void 내가_보낸_쪽지2() {
        ProgrammerResponse 개발자1 = 개발자(programmerId, programmerPassword);
        ProgrammerCreateRequest newProgrammer = new ProgrammerCreateRequest(
                "programmerId2",
                "programmerPassword345!",
                "개발자2",
                LocalDate.parse("2002-02-02"),
                "chu@gmail.com",
                2,
                List.of(Field.Back_End,Field.Front_End),
                "한줄소개",
                "없음");

        ProgrammerResponse 개발자2 = 개발자(newProgrammer.userId(), newProgrammer.password());

        CompanyMypageResponse 기업1 = 기업(companyId, companyPassword);

        AccessToken 기업1_토큰 = 기업로그인(companyId, companyPassword);

        MessageResponse 쪽지1 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new MessageRequest(개발자1.id()))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 기업1_토큰.token())
                .when()
                .post("/messages")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(MessageResponse.class);

        MessageResponse 쪽지2 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new MessageRequest(개발자2.id()))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 기업1_토큰.token())
                .when()
                .post("/messages")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(MessageResponse.class);

        List<MessageResponse> 쪽지들 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 기업1_토큰.token())
                .when()
                .get("/messages/senders")
                .then().log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList(".", MessageResponse.class);

        assertThat(쪽지들).hasSize(2);
        assertThat(쪽지들.get(0).senderOrReceiverName()).isEqualTo("chu");
    }

    @Test
    @DisplayName("내가 받은 쪽지 조회(내가 개발자일때)")
    void 내가_받은_쪽지1() {
        ProgrammerResponse 개발자1 = 개발자(programmerId, programmerPassword);

        CompanyMypageResponse 기업1 = 기업(companyId, companyPassword);

        CreateCompanyRequest newCompany = new CreateCompanyRequest(
                "companyId2",
                "companyPassword123!",
                "기업2",
                "kakao",
                Field.Front_End,
                "https://www.kakaocorp.com/page/",
                "주소",
                12,

                "한줄소개",
                LocalDate.parse("2025-01-24")
        );
        CompanyMypageResponse 기업2 = 기업(newCompany.userId(),newCompany.password());

        AccessToken 개발자1_토큰 = 개발자로그인(programmerId, programmerPassword);
        AccessToken 기업1_토큰 = 기업로그인(companyId, companyPassword);
        AccessToken 기업2_토큰 = 기업로그인(newCompany.userId(), newCompany.password());

        MessageResponse 쪽지1 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new MessageRequest(개발자1.id()))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 기업1_토큰.token())
                .when()
                .post("/messages")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(MessageResponse.class);

        MessageResponse 쪽지2 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new MessageRequest(개발자1.id()))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 기업2_토큰.token())
                .when()
                .post("/messages")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(MessageResponse.class);

        List<MessageResponse> 쪽지들 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 개발자1_토큰.token())
                .when()
                .get("/messages/receivers")
                .then().log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList(".", MessageResponse.class);

        assertThat(쪽지들).hasSize(2);

    }


    @Test
    @DisplayName("내가 받은 쪽지 조회 테스트 (내가 기업일때)")
    void 내가_받은_쪽지2() {
        ProgrammerResponse 개발자1 = 개발자(programmerId, programmerPassword);
        ProgrammerCreateRequest newProgrammer = new ProgrammerCreateRequest(
                "programmerId2",
                "programmerPassword345!",
                "개발자2",
                LocalDate.parse("2002-02-02"),
                "chu@gmail.com",
                2,
                List.of(Field.Back_End,Field.Front_End),
                "한줄소개",
                "없음");

        ProgrammerResponse 개발자2 = 개발자(newProgrammer.userId(), newProgrammer.password());

        CompanyMypageResponse 기업1 = 기업(companyId, companyPassword);

        AccessToken 기업1_토큰 = 기업로그인(companyId, companyPassword);
        AccessToken 개발자1_토큰 = 개발자로그인(programmerId, programmerPassword);
        AccessToken 개발자2_토큰 = 개발자로그인(newProgrammer.userId(), newProgrammer.password());

        MessageResponse 쪽지1 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new MessageRequest(기업1.id()))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 개발자1_토큰.token())
                .when()
                .post("/messages")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(MessageResponse.class);

        MessageResponse 쪽지2 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new MessageRequest(기업1.id()))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 개발자2_토큰.token())
                .when()
                .post("/messages")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(MessageResponse.class);

        List<MessageResponse> 쪽지들 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 기업1_토큰.token())
                .when()
                .get("/messages/receivers")
                .then().log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList(".", MessageResponse.class);

        assertThat(쪽지들).hasSize(2);
    }

    @Test
    @DisplayName("내가 보낸 쪽지 삭제1(내가 개발자일때)")
    void 보낸_쪽지_삭제1() {
        ProgrammerResponse 개발자1 = 개발자(programmerId, programmerPassword);

        CompanyMypageResponse 기업1 = 기업(companyId, companyPassword);
        CreateCompanyRequest newCompany = new CreateCompanyRequest(
                "companyId2",
                "companyPassword123!",
                "기업2",
                "kakao",
                Field.Front_End,
                "https://www.kakaocorp.com/page/",
                "주소",
                12,

                "한줄소개",
                LocalDate.parse("2025-01-24")
        );
        CompanyMypageResponse 기업2 = 기업(newCompany.userId(),newCompany.password());


        //개발자 로그인
        AccessToken 개발자1_토큰 = 개발자로그인(programmerId, programmerPassword);
        AccessToken 기업1_토큰 = 기업로그인(companyId, companyPassword);

        MessageResponse 쪽지1 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new MessageRequest(기업1.id()))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 개발자1_토큰.token())
                .when()
                .post("/messages")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(MessageResponse.class);

        MessageResponse 쪽지2 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new MessageRequest(기업2.id()))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 개발자1_토큰.token())
                .when()
                .post("/messages")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(MessageResponse.class);

        RestAssured
                .given().log().all()
                .pathParam("messageId", 쪽지2.id())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 개발자1_토큰.token())
                .when()
                .delete("/messages/{messageId}")
                .then().log().all()
                .statusCode(200);

        List<MessageResponse> 개발자의쪽지들 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 개발자1_토큰.token())
                .when()
                .get("/messages/senders")
                .then().log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList(".", MessageResponse.class);

        List<MessageResponse> 기업1의쪽지들 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 기업1_토큰.token())
                .when()
                .get("/messages/receivers")
                .then().log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList(".", MessageResponse.class);

        assertThat(개발자의쪽지들).hasSize(1);
        assertThat(기업1의쪽지들).hasSize(1);

    }

    @Test
    @DisplayName("내가 보낸 쪽지 삭제 테스트(내가 기업일때)")
    void 보낸_쪽지_삭제2() {
        ProgrammerResponse 개발자1 = 개발자(programmerId, programmerPassword);
        ProgrammerCreateRequest newProgrammer = new ProgrammerCreateRequest(
                "programmerId2",
                "programmerPassword345!",
                "개발자2",
                LocalDate.parse("2002-02-02"),
                "chu@gmail.com",
                2,
                List.of(Field.Back_End,Field.Front_End),
                "한줄소개",
                "없음");

        ProgrammerResponse 개발자2 = 개발자(newProgrammer.userId(), newProgrammer.password());

        CompanyMypageResponse 기업1 = 기업(companyId, companyPassword);

        AccessToken 기업1_토큰 = 기업로그인(companyId, companyPassword);
        AccessToken 개발자1_토큰 = 개발자로그인(programmerId, programmerPassword);

        MessageResponse 쪽지1 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new MessageRequest(개발자1.id()))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 기업1_토큰.token())
                .when()
                .post("/messages")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(MessageResponse.class);

        MessageResponse 쪽지2 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new MessageRequest(개발자2.id()))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 기업1_토큰.token())
                .when()
                .post("/messages")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(MessageResponse.class);

        RestAssured
                .given().log().all()
                .pathParam("messageId", 쪽지2.id())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 기업1_토큰.token())
                .when()
                .delete("/messages/{messageId}")
                .then().log().all()
                .statusCode(200);

        List<MessageResponse> 기업1의쪽지들 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 기업1_토큰.token())
                .when()
                .get("/messages/senders")
                .then().log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList(".", MessageResponse.class);

        List<MessageResponse> 개발자의쪽지들 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 개발자1_토큰.token())
                .when()
                .get("/messages/receivers")
                .then().log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList(".", MessageResponse.class);

        assertThat(기업1의쪽지들).hasSize(1);
        assertThat(개발자의쪽지들).hasSize(1);

    }

    @Test
    @DisplayName("내가 받은 쪽지 삭제1(내가 개발자일때)")
    void 받은_쪽지_삭제1() {
        ProgrammerResponse 개발자1 = 개발자(programmerId, programmerPassword);

        CompanyMypageResponse 기업1 = 기업(companyId, companyPassword);

        CreateCompanyRequest newCompany = new CreateCompanyRequest(
                "companyId2",
                "companyPassword123!",
                "기업2",
                "kakao",
                Field.Front_End,
                "https://www.kakaocorp.com/page/",
                "주소",
                12,

                "한줄소개",
                LocalDate.parse("2025-01-24")
        );
        CompanyMypageResponse 기업2 = 기업(newCompany.userId(),newCompany.password());

        AccessToken 개발자1_토큰 = 개발자로그인(programmerId, programmerPassword);
        AccessToken 기업1_토큰 = 기업로그인(companyId, companyPassword);
        AccessToken 기업2_토큰 = 기업로그인(newCompany.userId(), newCompany.password());

        MessageResponse 쪽지1 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new MessageRequest(개발자1.id()))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 기업1_토큰.token())
                .when()
                .post("/messages")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(MessageResponse.class);

        MessageResponse 쪽지2 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new MessageRequest(개발자1.id()))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 기업2_토큰.token())
                .when()
                .post("/messages")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(MessageResponse.class);

        List<MessageResponse> 삭제전_개발자1쪽지들 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 개발자1_토큰.token())
                .when()
                .get("/messages/receivers")
                .then().log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList(".", MessageResponse.class);

        RestAssured
                .given().log().all()
                .pathParam("messageId", 쪽지2.id())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 개발자1_토큰.token())
                .when()
                .delete("/messages/{messageId}")
                .then().log().all()
                .statusCode(200);

        List<MessageResponse> 삭제후_개발자1쪽지들 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 개발자1_토큰.token())
                .when()
                .get("/messages/receivers")
                .then().log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList(".", MessageResponse.class);

        List<MessageResponse> 기업2쪽지들 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 기업2_토큰.token())
                .when()
                .get("/messages/senders")
                .then().log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList(".", MessageResponse.class);

        assertThat(삭제전_개발자1쪽지들).hasSize(2);
        assertThat(삭제후_개발자1쪽지들).hasSize(1);
        assertThat(기업2쪽지들).hasSize(1);

    }

    @Test
    @DisplayName("내가 받은 쪽지 삭제2(내가 기업일때)")
    void 받은_쪽지_삭제2() {
        ProgrammerResponse 개발자1 = 개발자(programmerId, programmerPassword);
        ProgrammerCreateRequest newProgrammer = new ProgrammerCreateRequest(
                "programmerId2",
                "programmerPassword345!",
                "개발자2",
                LocalDate.parse("2002-02-02"),
                "chu@gmail.com",
                2,
                List.of(Field.Back_End,Field.Front_End),
                "한줄소개",
                "없음");

        ProgrammerResponse 개발자2 = 개발자(newProgrammer.userId(), newProgrammer.password());

        CompanyMypageResponse 기업1 = 기업(companyId, companyPassword);

        AccessToken 기업1_토큰 = 기업로그인(companyId, companyPassword);
        AccessToken 개발자1토큰 = 개발자로그인(programmerId, programmerPassword);
        AccessToken 개발자2토큰 = 개발자로그인(newProgrammer.userId(), newProgrammer.password());

        MessageResponse 쪽지1 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new MessageRequest(기업1.id()))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 개발자1토큰.token())
                .when()
                .post("/messages")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(MessageResponse.class);

        MessageResponse 쪽지2 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new MessageRequest(기업1.id()))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 개발자2토큰.token())
                .when()
                .post("/messages")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(MessageResponse.class);

        List<MessageResponse> 삭제전_기업1쪽지들 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 기업1_토큰.token())
                .when()
                .get("/messages/receivers")
                .then().log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList(".", MessageResponse.class);

        RestAssured
                .given().log().all()
                .pathParam("messageId", 쪽지2.id())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 기업1_토큰.token())
                .when()
                .delete("/messages/{messageId}")
                .then().log().all()
                .statusCode(200);

        List<MessageResponse> 삭제후_기업1쪽지들 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 기업1_토큰.token())
                .when()
                .get("/messages/receivers")
                .then().log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList(".", MessageResponse.class);

        List<MessageResponse> 개발자2쪽지들 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 개발자2토큰.token())
                .when()
                .get("/messages/senders")
                .then().log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList(".", MessageResponse.class);

        assertThat(삭제전_기업1쪽지들).hasSize(2);
        assertThat(삭제후_기업1쪽지들).hasSize(1);
        assertThat(개발자2쪽지들).hasSize(1);

    }
}
