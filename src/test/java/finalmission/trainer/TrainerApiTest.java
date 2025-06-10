package finalmission.trainer;

import static org.hamcrest.Matchers.is;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import finalmission.trainer.dto.request.TrainerCreateRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class TrainerApiTest {

    @LocalServerPort
    int port;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("트레이너를 추가할 수 있다.")
    @Test
    void addTrainerTest1(){
        // given

        // when

        Map<String, Object> request = new HashMap<>();
        request.put("name", "name");
        request.put("birth", "2000-12-18");

        // then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/trainers")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .body("birth", is("2000-12-18"))
                .body("name", is("name"));

    }

}
