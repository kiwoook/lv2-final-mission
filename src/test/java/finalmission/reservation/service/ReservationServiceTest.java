package finalmission.reservation.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import finalmission.common.exception.NotFoundException;
import finalmission.fixture.TrainerDbFixture;
import finalmission.reservation.domain.Reservation;
import finalmission.reservation.domain.ReservationStatus;
import finalmission.reservation.dto.request.ReservationCreateRequest;
import finalmission.reservation.dto.response.ReservationInfoResponse;
import finalmission.reservation.exception.InAlreadyReservationException;
import finalmission.reservation.repository.ReservationRepository;
import finalmission.trainer.domain.Trainer;
import java.time.LocalDateTime;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private TrainerDbFixture trainerDbFixture;

    @Autowired
    private ReservationRepository reservationRepository;


    @DisplayName("예약 생성 테스트")
    @Test
    void createReservationTest1() {
        // given
        Long trainerId = trainerDbFixture.createTrainer1().getId();
        LocalDateTime reservationDateTime = LocalDateTime.now();
        ReservationCreateRequest reservationCreateRequest = new ReservationCreateRequest(reservationDateTime,
                trainerId);

        // when
        ReservationInfoResponse result = reservationService.create(reservationCreateRequest);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(result.reservationDateTime()).isEqualTo(reservationDateTime);
            softly.assertThat(result.status()).isEqualTo(ReservationStatus.AVAILABLE);
            softly.assertThat(result.trainerInfo().id()).isEqualTo(trainerId);
        });
    }

    @DisplayName("트레이너가 존재하지 않으면 예외를 반환한다.")
    @Test
    void createReservation_throwsException() {
        // given
        LocalDateTime reservationDateTime = LocalDateTime.now();
        ReservationCreateRequest reservationCreateRequest = new ReservationCreateRequest(reservationDateTime,
                1L);

        // when & then
        assertThatThrownBy(() -> {
            reservationService.create(reservationCreateRequest);
        }).isInstanceOf(NotFoundException.class);
    }

    @DisplayName("중복된 시간이면 예외를 반환한다")
    @Test
    void createReservation_throwsException2() {
        // given
        Trainer trainer1 = trainerDbFixture.createTrainer1();
        LocalDateTime reservationDateTime = LocalDateTime.now();
        Reservation reservation1 = Reservation.create(reservationDateTime, trainer1);

        reservationRepository.save(reservation1);

        ReservationCreateRequest createRequest = new ReservationCreateRequest(reservationDateTime,
                trainer1.getId());

        // when & then
        assertThatThrownBy(() -> {
            reservationService.create(createRequest);
        }).isInstanceOf(InAlreadyReservationException.class);
    }

    @DisplayName("예약 가능한 날짜를 모두 반환한다")
    @Test
    void getAvailableReservationsTest() {
        // given
        Trainer trainer1 = trainerDbFixture.createTrainer1();
        LocalDateTime reservationDateTime = LocalDateTime.now();
        Reservation reservation1 = Reservation.create(reservationDateTime, trainer1);
        Reservation reservation2 = Reservation.create(reservationDateTime.plusHours(1), trainer1);
        Reservation reservation3 = Reservation.create(reservationDateTime.plusHours(2), trainer1);

        reservation3.updateStatus(ReservationStatus.COMPLETE);

        reservationRepository.saveAll(List.of(reservation1, reservation2, reservation3));

        // when
        List<ReservationInfoResponse> result = reservationService.getAvailableReservations();

        // then
        assertThat(result).hasSize(2);
    }
}