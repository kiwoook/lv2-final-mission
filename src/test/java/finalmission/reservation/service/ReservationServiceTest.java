package finalmission.reservation.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import finalmission.common.exception.InvalidArgumentException;
import finalmission.common.exception.NotFoundException;
import finalmission.fixture.db.TrainerDbFixture;
import finalmission.reservation.domain.Reservation;
import finalmission.reservation.domain.ReservationStatus;
import finalmission.reservation.dto.request.ReservationCreateRequest;
import finalmission.reservation.dto.response.ReservationInfoResponse;
import finalmission.reservation.exception.InAlreadyReservationException;
import finalmission.reservation.exception.InvalidCreateReservationException;
import finalmission.reservation.repository.ReservationRepository;
import finalmission.trainer.domain.Trainer;
import finalmission.utils.DbCleanUp;
import java.time.LocalDateTime;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private TrainerDbFixture trainerDbFixture;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private DbCleanUp dbCleanUp;

    @BeforeEach
    void setUp() {
        dbCleanUp.all();
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

    @DisplayName("사용 가능한 날짜에만 예약을 할 수 잇다.")
    @Test
    void update_throwsException() {
        // given

        // when & then
        assertThatThrownBy(() -> {

        }).isInstanceOf(InAlreadyReservationException.class);
    }

    @Nested
    class create {
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

        @DisplayName("공휴일에는 예약을 생성할 수 없다.")
        @Test
        void createReservationTest2() {
            // given
            Long trainerId = trainerDbFixture.createTrainer1().getId();
            LocalDateTime reservationDateTime = LocalDateTime.of(2025, 10, 9, 10, 0);
            ReservationCreateRequest reservationCreateRequest = new ReservationCreateRequest(reservationDateTime,
                    trainerId);

            // when
            // then
            assertThatThrownBy(() -> reservationService.create(reservationCreateRequest))
                    .isInstanceOf(InvalidCreateReservationException.class);
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
            LocalDateTime reservationDateTime = LocalDateTime.of(2025, 12, 18, 10, 0);
            Reservation reservation1 = Reservation.create(reservationDateTime, trainer1);

            reservationRepository.save(reservation1);

            ReservationCreateRequest createRequest = new ReservationCreateRequest(reservationDateTime,
                    trainer1.getId());

            // when & then
            assertThatThrownBy(() -> {
                reservationService.create(createRequest);
            }).isInstanceOf(InAlreadyReservationException.class);
        }
    }

    @Nested
    class reserved {
        @DisplayName("예약을 할 수 있다.")
        @Test
        void reservedTest1() {
            // given

            // when

            // then

        }

        @DisplayName("사용 중인 예약은 할 수 없다.")
        @Test
        void reserved_throwsException() {
            // given

            // when & then
            assertThatThrownBy(() -> {

            }).isInstanceOf(InAlreadyReservationException.class);
        }
    }

    @Nested
    class cancel {
        @DisplayName("예약을 취소할 수 있다.")
        @Test
        void cancelTest1() {
            // given

            // when

            // then

        }

        @DisplayName("취소 시 해당 예약자가 아니라면 예외를 반환한다. ")
        @Test
        void cancel_throwsException() {
            // given

            // when & then
            assertThatThrownBy(() -> {

            }).isInstanceOf(InvalidArgumentException.class);
        }
    }
}
