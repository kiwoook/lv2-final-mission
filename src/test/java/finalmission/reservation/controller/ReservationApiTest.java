package finalmission.reservation.controller;

import static org.hamcrest.Matchers.is;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import finalmission.auth.utils.TokenUtil;
import finalmission.fixture.db.MemberDbFixture;
import finalmission.fixture.db.ReservationDbFixture;
import finalmission.fixture.db.ReservedDbFixture;
import finalmission.member.domain.Member;
import finalmission.reservation.domain.Reservation;
import finalmission.reservation.domain.Reserved;
import finalmission.reservation.dto.request.ReserveChangeRequest;
import finalmission.reservation.dto.request.ReserveRequest;
import finalmission.reservation.repository.ReservationRepository;
import finalmission.reservation.repository.ReservedRepository;
import finalmission.utils.DbCleanUp;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ReservationApiTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private ReservationDbFixture reservationDbFixture;

    @Autowired
    private ReservedDbFixture reservedDbFixture;

    @Autowired
    private MemberDbFixture memberDbFixture;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ReservedRepository reservedRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private DbCleanUp cleanUp;


    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        cleanUp.all();
    }

    @DisplayName("예약을 할 수 있다.")
    @Test
    void reserveTest1() throws JsonProcessingException {
        // given
        Member user = memberDbFixture.createUser();
        Reservation reservation = reservationDbFixture.creatReservation1();
        ReserveRequest reserveRequest = new ReserveRequest(reservation.getId());
        String token = tokenUtil.createToken(user);

        // when
        // then
        RestAssured.given().log().all()
                .cookie("token", token)
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(reserveRequest))
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200);
    }

    @DisplayName("예약을 취소할 수 있다.")
    @Test
    void cancelTest1() {
        // given
        Reserved reserved = reservedDbFixture.createReserved();
        String token = tokenUtil.createToken(reserved.getMember());

        // when
        // then
        RestAssured.given().log().all()
                .cookie("token", token)
                .when().delete("/reservations/" + reserved.getId())
                .then().log().all()
                .statusCode(204);
    }

    @DisplayName("예약을 변경할 수 있다.")
    @Test
    void changeTest1() throws JsonProcessingException {
        // given
        Reserved reserved = reservedDbFixture.createReserved();
        Reservation reservation2 = reservationDbFixture.creatReservation2();

        ReserveChangeRequest reserveChangeRequest = new ReserveChangeRequest(reserved.getId(), reservation2.getId());
        String token = tokenUtil.createToken(reserved.getMember());

        // when
        // then
        RestAssured.given().log().all()
                .cookie("token", token)
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(reserveChangeRequest))
                .when().put("/reservations")
                .then().log().all()
                .statusCode(200);
    }

    @DisplayName("나의 모든 예약을 확인할 수 있다")
    @Test
    void getAllMyReservationsTest1() {
        // given
        Reservation reservation1 = reservationDbFixture.creatReservation1();
        Reservation reservation2 = reservationDbFixture.creatReservation2();
        Member user = memberDbFixture.createUser();

        Reserved reserved1 = new Reserved(user, reservation1);
        Reserved reserved2 = new Reserved(user, reservation2);

        reservationRepository.saveAll(List.of(reservation1, reservation2));
        reservedRepository.saveAll(List.of(reserved1, reserved2));
        String token = tokenUtil.createToken(user);

        // when
        // then
        RestAssured.given().log().all()
                .cookie("token", token)
                .when().get("/reservations/me")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(2));
    }


    @DisplayName("이용 가능한 예약들을 확인할 수 있다.")
    @Test
    void getAvailableReservationsTest1() {
        // given
        reservationDbFixture.creatReservation1();
        reservationDbFixture.creatReservation2();
        reservationDbFixture.createCompletedReservation();
        // when

        // then
        RestAssured.given().log().all()
                .when().get("/reservations/available")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(2));
    }
}