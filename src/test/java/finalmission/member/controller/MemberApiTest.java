package finalmission.member.controller;

import static org.hamcrest.Matchers.is;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import finalmission.member.dto.request.MemberCreateRequest;
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
class MemberApiTest {

    @LocalServerPort
    int port;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("회원가입을 할 수 있다.")
    @Test
    void signUpTest1() throws JsonProcessingException {
        // given

        String email = "tisad@naver.com";
        MemberCreateRequest memberCreateRequest = new MemberCreateRequest(email, "1234");

        // when
        // then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(memberCreateRequest))
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .body("email", is(email));
    }
}
