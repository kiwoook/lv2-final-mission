package finalmission.reservation.repository;

import static org.assertj.core.api.Assertions.assertThat;

import finalmission.fixture.db.TrainerDbFixture;
import finalmission.reservation.domain.Reservation;
import finalmission.trainer.domain.Trainer;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(TrainerDbFixture.class)
class ReservationRepositoryTest {

    @Autowired
    private TrainerDbFixture trainerDbFixture;

    @Autowired
    private ReservationRepository reservationRepository;

    @DisplayName("날짜와 트레이너 ID가 존재하면 true를 반환한다.")
    @Test
    void existsTest1() {
        // given
        Trainer trainer1 = trainerDbFixture.createTrainer1();
        LocalDateTime now = LocalDateTime.of(2025, 12, 18, 10, 0);
        Reservation reservation = Reservation.create(now, trainer1);
        reservationRepository.save(reservation);

        // when
        boolean result = reservationRepository.existsByReservationDateTimeAndTrainerId(now, trainer1.getId());

        // then
        assertThat(result).isTrue();
    }
}