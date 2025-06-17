package finalmission.reservation.controller;

import finalmission.auth.utils.TokenUtils;
import finalmission.fixture.db.TrainerDbFixture;
import finalmission.trainer.domain.Trainer;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ReservationAdminApiTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TrainerDbFixture trainerDbFixture;

    @Autowired
    private TokenUtils tokenUtils;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("예약을 생성한다")
    @Test
    void createTest1() {
        // given
        Trainer trainer1 = trainerDbFixture.createTrainer1();

        Map<String, Object> request = new HashMap<>();
        request.put("reservationDateTime", "2025-12-18 10:06");
        request.put("trainerId", trainer1.getId());

        // when
        // then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", tokenUtils.createAdminToken())
                .body(request)
                .when().post("/admin/reservations")
                .then().log().all()
                .statusCode(201);
    }
}