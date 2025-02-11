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

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
                        "userId1234",
                        "abcDEF123!",
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

        assertThat(개발자).isNotNull();
    }
    @Test
    void 개발자_상세조회() {
        ProgrammerResponse 개발자 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new ProgrammerCreateRequest(
                        "userId1234",
                        "abcDEF123!",
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

        //로그인
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
        assertThat(개발자.age()).isEqualTo(24);
    }

    @Test
    void 개발자_내정보_상세조회() {
        ProgrammerResponse 개발자 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new ProgrammerCreateRequest(
                        "userId1234",
                        "abcDEF123!",
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

        //로그인
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
                        "userId1234",
                        "abcDEF123!",
                        수정전이름,
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

        //로그인
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

        ProgrammerResponse 개발자수정 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                // TODO: "token" 실제 코드 작성
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token.token())
                .body(new ProgrammerRequest(
                        "userId1234",
                        수정후이름,
                        LocalDate.parse("2001-01-01"),
                        "emailtest@gmail.com",
                        1,
                        List.of(Field.Back_End,Field.Front_End),
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
                        "userId1234",
                        수정전비번,
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

        //로그인
        AccessToken token = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new LoginRequest(
                        "userId1234",
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
                        "userId1234",
                        "abcDEF123!",
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

        //로그인
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



    //만나이 계산
    public class Person {
        private LocalDate birthDate;  // 생일
        private int age;

        public Person(LocalDate birthDate) {
            this.birthDate = birthDate;
        }

        public void countAge() {

            LocalDate currentDate = LocalDate.of(2025,02,04);

            int calculatedAge = currentDate.getYear() - birthDate.getYear();

            //월 안지났으면 -1 / 일 안지났으면 -1
            if (currentDate.getMonthValue() < birthDate.getMonthValue()){
                calculatedAge --;
            }
            if (currentDate.getMonthValue() == birthDate.getMonthValue() && currentDate.getDayOfMonth() < birthDate.getDayOfMonth()){
                calculatedAge --;
            }

            age = calculatedAge;
        }

        public int getAge() {
            return age;
        }
    }

    @Test
    public void 만나이계산() {
        // 생일: 1990년 2월 4일
        Person person = new Person(LocalDate.of(1990, 2, 4));

        // countAge() 메서드 호출
        person.countAge();

        // 2025년 2월 4일 기준으로 나이는 35세여야 함
        assertEquals(35, person.getAge());
    }


}

