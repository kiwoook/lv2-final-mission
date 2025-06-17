package finalmission.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import finalmission.auth.provider.JwtProvider;
import finalmission.auth.web.controller.request.LoginRequest;
import finalmission.fixture.db.MemberDbFixture;
import finalmission.member.domain.Email;
import finalmission.member.domain.Member;
import finalmission.member.domain.Password;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class AuthApiTest {

    @LocalServerPort
    int port;

    @Autowired
    private MemberDbFixture memberDbFixture;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private ObjectMapper objectMapper;

    private Member member;

    private String token;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;

        member = memberDbFixture.createUser();
        token = jwtProvider.generateJwt(member);
    }

    @DisplayName("로그인을 할 수 있다.")
    @Test
    void loginTest1() throws JsonProcessingException {
        // given
        Email email = member.getEmail();
        Password password = member.getPassword();

        LoginRequest loginRequest = new LoginRequest(email.email(), password.password());
        // when
        // then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(loginRequest))
                .when().post("/login")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
        // 쿠키 값 존재 검증
    }

    @DisplayName("잘못된 비밀번호는 401 상태 코드를 반환한다.")
    @Test
    void loginFailedTest1() throws JsonProcessingException {
        // given
        Email email = member.getEmail();
        String wrongPassword = "ascxzvcxv";

        LoginRequest loginRequest = new LoginRequest(email.email(), wrongPassword);
        // when

        // then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(loginRequest))
                .when().post("/login")
                .then().log().all()
                .statusCode(401);
    }
}
