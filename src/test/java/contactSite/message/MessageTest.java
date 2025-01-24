package contactSite.message;

import contactSite.Field;
import contactSite.Login.LoginRequest;
import contactSite.LoginUtils.AccessToken;
import contactSite.LoginUtils.JwtProvider;
import contactSite.company.CompanyMypageResponse;
import contactSite.company.CreateCompanyRequest;
import contactSite.message.dto.MessageRequest;
import contactSite.message.dto.MessageResponse;
import contactSite.message.dto.MessageSendResponse;
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

    private String programmerId = "programmerId1";
    private String programmerPassword = "programmerPassword123!";

    private String companyId = "companyId1";
    private String companyPassword = "companyPassword123!";

    private ProgrammerResponse 개발자_생성(String programmerId, String programmerPassword){
        return RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new ProgrammerCreateRequest(
                        programmerId,
                        programmerPassword,
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
    }

    private CompanyMypageResponse 기업_생성 (String companyId, String companyPassword){
        return RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new CreateCompanyRequest(
                        companyId,
                        companyPassword,
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
        ProgrammerResponse 개발자 = 개발자_생성(programmerId, programmerPassword);

        CompanyMypageResponse 기업 = 기업_생성(companyId, companyPassword);

        AccessToken token = 개발자로그인(programmerId, programmerPassword);

        MessageResponse 개발자가_기업에게_보내는_쪽지 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new MessageRequest(기업.id()))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token.token())
                .when()
                .post("/messages")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(MessageResponse.class);

        assertThat(개발자가_기업에게_보내는_쪽지).isNotNull();
    }

    @Test
    @DisplayName("기업이 개발자에게 쪽지 보내는 테스트")
    void 쪽지생성2() {
        ProgrammerResponse 개발자 = 개발자_생성(programmerId, programmerPassword);

        CompanyMypageResponse 기업 = 기업_생성(companyId, companyPassword);

        AccessToken token = 기업로그인(companyId, companyPassword);

        MessageResponse 쪽지 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new MessageRequest(기업.id()))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token.token())
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
        ProgrammerResponse 개발자 = 개발자_생성(programmerId, programmerPassword);

        CompanyMypageResponse 기업1 = 기업_생성(companyId, companyPassword);
        CreateCompanyRequest newCompany = new CreateCompanyRequest(
                "companyId2",
                "companyPassword123!",
                "기업2",
                "kakao",
                Field.Front_End,
                "www://webseit",
                "주소",
                12,

                "한줄소개",
                2002
        );
        CompanyMypageResponse 기업2 = 기업_생성(newCompany.userId(),newCompany.password());


        //개발자 로그인
        AccessToken token = 개발자로그인(programmerId, programmerPassword);

        MessageResponse 쪽지1 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new MessageRequest(기업1.id()))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token.token())
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
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token.token())
                .when()
                .post("/messages")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(MessageResponse.class);

        List<MessageSendResponse> 쪽지들 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token.token())
                .when()
                .get("/messages/senders")
                .then().log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList(".", MessageSendResponse.class);

        assertThat(쪽지들).hasSize(2);

    }

    @Test
    @DisplayName("내가 보낸 쪽지(내가 기업일때)")
    void 내가_보낸_쪽지2() {
        ProgrammerResponse 개발자1 = 개발자_생성(programmerId, programmerPassword);
        ProgrammerCreateRequest newProgrammer = new ProgrammerCreateRequest(
                "programmerId2",
                "programmerPassword345!",
                "개발자2",
                23,
                "chu@gmail.com",
                2,
                Field.Back_End,
                "한줄소개",
                "없음");

        ProgrammerResponse 개발자2 = 개발자_생성(newProgrammer.userId(), newProgrammer.password());

        CompanyMypageResponse 기업1 = 기업_생성(companyId, companyPassword);

        AccessToken token = 기업로그인(companyId, companyPassword);

        MessageResponse 쪽지1 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new MessageRequest(개발자1.id()))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token.token())
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
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token.token())
                .when()
                .post("/messages")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(MessageResponse.class);

        List<MessageSendResponse> 쪽지들 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token.token())
                .when()
                .get("/messages/senders")
                .then().log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList(".", MessageSendResponse.class);

        assertThat(쪽지들).hasSize(2);
    }

    @Test
    @DisplayName("내가 받은 쪽지 조회(내가 개발자일때)")
    void 내가_받은_쪽지1() {
        ProgrammerResponse 개발자 = 개발자_생성(programmerId, programmerPassword);

        CompanyMypageResponse 기업1 = 기업_생성(companyId, companyPassword);

        CreateCompanyRequest newCompany = new CreateCompanyRequest(
                "companyId2",
                "companyPassword123!",
                "기업2",
                "kakao",
                Field.Front_End,
                "www://webseit",
                "주소",
                12,

                "한줄소개",
                2002
        );
        CompanyMypageResponse 기업2 = 기업_생성(newCompany.userId(),newCompany.password());

        AccessToken 개발자토큰 = 개발자로그인(programmerId, programmerPassword);
        AccessToken 기업1토큰 = 기업로그인(companyId, companyPassword);
        AccessToken 기업2토큰 = 기업로그인(newCompany.userId(), newCompany.password());

        MessageResponse 쪽지1 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new MessageRequest(개발자.id()))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 기업1토큰.token())
                .when()
                .post("/messages")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(MessageResponse.class);

        MessageResponse 쪽지2 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new MessageRequest(개발자.id()))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 기업2토큰.token())
                .when()
                .post("/messages")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(MessageResponse.class);

        List<MessageSendResponse> 쪽지들 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 개발자토큰.token())
                .when()
                .get("/messages/receivers")
                .then().log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList(".", MessageSendResponse.class);

        assertThat(쪽지들).hasSize(2);

    }


    @Test
    @DisplayName("내가 받은 쪽지 조회 테스트 (내가 기업일때)")
    void 내가_받은_쪽지2() {
        ProgrammerResponse 개발자1 = 개발자_생성(programmerId, programmerPassword);
        ProgrammerCreateRequest newProgrammer = new ProgrammerCreateRequest(
                "programmerId2",
                "programmerPassword345!",
                "개발자2",
                23,
                "chu@gmail.com",
                2,
                Field.Back_End,
                "한줄소개",
                "없음");

        ProgrammerResponse 개발자2 = 개발자_생성(newProgrammer.userId(), newProgrammer.password());

        CompanyMypageResponse 기업1 = 기업_생성(companyId, companyPassword);

        AccessToken 기업토큰 = 기업로그인(companyId, companyPassword);
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

        List<MessageSendResponse> 쪽지들 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 기업토큰.token())
                .when()
                .get("/messages/receivers")
                .then().log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList(".", MessageSendResponse.class);

        assertThat(쪽지들).hasSize(2);
    }

    @Test
    @DisplayName("내가 보낸 쪽지 삭제1(내가 개발자일때)")
    void 보낸_쪽지_삭제1() {
        ProgrammerResponse 개발자 = 개발자_생성(programmerId, programmerPassword);

        CompanyMypageResponse 기업1 = 기업_생성(companyId, companyPassword);
        CreateCompanyRequest newCompany = new CreateCompanyRequest(
                "companyId2",
                "companyPassword123!",
                "기업2",
                "kakao",
                Field.Front_End,
                "www://webseit",
                "주소",
                12,

                "한줄소개",
                2002
        );
        CompanyMypageResponse 기업2 = 기업_생성(newCompany.userId(),newCompany.password());


        //개발자 로그인
        AccessToken 개발자토큰 = 개발자로그인(programmerId, programmerPassword);

        MessageResponse 쪽지1 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new MessageRequest(기업1.id()))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 개발자토큰.token())
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
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 개발자토큰.token())
                .when()
                .post("/messages")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(MessageResponse.class);

        List<MessageSendResponse> 쪽지들 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 개발자토큰.token())
                .when()
                .get("/messages/senders")
                .then().log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList(".", MessageSendResponse.class);

        RestAssured
                .given().log().all()
                .pathParam("messageId", 쪽지2.id())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 개발자토큰.token())
                .when()
                .delete("/messages/senders/{messageId}")
                .then().log().all()
                .statusCode(200);

    }

    @Test
    @DisplayName("내가 보낸 쪽지 삭제 테스트(내가 기업일때)")
    void 보낸_쪽지_삭제2() {
        ProgrammerResponse 개발자1 = 개발자_생성(programmerId, programmerPassword);
        ProgrammerCreateRequest newProgrammer = new ProgrammerCreateRequest(
                "programmerId2",
                "programmerPassword345!",
                "개발자2",
                23,
                "chu@gmail.com",
                2,
                Field.Back_End,
                "한줄소개",
                "없음");

        ProgrammerResponse 개발자2 = 개발자_생성(newProgrammer.userId(), newProgrammer.password());

        CompanyMypageResponse 기업1 = 기업_생성(companyId, companyPassword);

        AccessToken 기업토큰 = 기업로그인(companyId, companyPassword);

        MessageResponse 쪽지1 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new MessageRequest(개발자1.id()))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 기업토큰.token())
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
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 기업토큰.token())
                .when()
                .post("/messages")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(MessageResponse.class);

        RestAssured
                .given().log().all()
                .pathParam("messageId", 쪽지2.id())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 기업토큰.token())
                .when()
                .delete("/messages/senders/{messageId}")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    @DisplayName("내가 받은 쪽지 삭제1(내가 개발자일때)")
    void 받은_쪽지_삭제1() {
        ProgrammerResponse 개발자 = 개발자_생성(programmerId, programmerPassword);

        CompanyMypageResponse 기업1 = 기업_생성(companyId, companyPassword);

        CreateCompanyRequest newCompany = new CreateCompanyRequest(
                "companyId2",
                "companyPassword123!",
                "기업2",
                "kakao",
                Field.Front_End,
                "www://webseit",
                "주소",
                12,

                "한줄소개",
                2002
        );
        CompanyMypageResponse 기업2 = 기업_생성(newCompany.userId(),newCompany.password());

        AccessToken 개발자토큰 = 개발자로그인(programmerId, programmerPassword);
        AccessToken 기업1토큰 = 기업로그인(companyId, companyPassword);
        AccessToken 기업2토큰 = 기업로그인(newCompany.userId(), newCompany.password());

        MessageResponse 쪽지1 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new MessageRequest(개발자.id()))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 기업1토큰.token())
                .when()
                .post("/messages")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(MessageResponse.class);

        MessageResponse 쪽지2 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new MessageRequest(개발자.id()))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 기업2토큰.token())
                .when()
                .post("/messages")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(MessageResponse.class);

        List<MessageSendResponse> 쪽지들 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 개발자토큰.token())
                .when()
                .get("/messages/receivers")
                .then().log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList(".", MessageSendResponse.class);

        RestAssured
                .given().log().all()
                .pathParam("messageId", 쪽지2.id())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 개발자토큰.token())
                .when()
                .delete("/messages/receivers/{messageId}")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    @DisplayName("내가 받은 쪽지 삭제2(내가 기업일때)")
    void 받은_쪽지_삭제2() {
        ProgrammerResponse 개발자1 = 개발자_생성(programmerId, programmerPassword);
        ProgrammerCreateRequest newProgrammer = new ProgrammerCreateRequest(
                "programmerId2",
                "programmerPassword345!",
                "개발자2",
                23,
                "chu@gmail.com",
                2,
                Field.Back_End,
                "한줄소개",
                "없음");

        ProgrammerResponse 개발자2 = 개발자_생성(newProgrammer.userId(), newProgrammer.password());

        CompanyMypageResponse 기업1 = 기업_생성(companyId, companyPassword);

        AccessToken 기업토큰 = 기업로그인(companyId, companyPassword);
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

        List<MessageSendResponse> 쪽지들 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 기업토큰.token())
                .when()
                .get("/messages/receivers")
                .then().log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList(".", MessageSendResponse.class);

        RestAssured
                .given().log().all()
                .pathParam("messageId", 쪽지2.id())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 기업토큰.token())
                .when()
                .delete("/messages/receivers/{messageId}")
                .then().log().all()
                .statusCode(200);

    }
}
