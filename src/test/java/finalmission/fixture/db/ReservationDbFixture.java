package finalmission.fixture.db;

import finalmission.reservation.domain.Reservation;
import finalmission.reservation.domain.ReservationStatus;
import finalmission.reservation.repository.ReservationRepository;
import finalmission.trainer.domain.Trainer;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReservationDbFixture {

    @Autowired
    private TrainerDbFixture trainerDbFixture;

    @Autowired
    private ReservationRepository reservationRepository;

    public Reservation creatReservation1() {
        Trainer trainer1 = trainerDbFixture.createTrainer1();
        LocalDateTime time = LocalDateTime.of(2025, 12, 18, 10, 0);
        return reservationRepository.save(Reservation.create(time, trainer1));
    }

    public Reservation creatReservation2() {
        Trainer trainer1 = trainerDbFixture.createTrainer1();
        LocalDateTime time = LocalDateTime.of(2025, 12, 18, 12, 0);
        return reservationRepository.save(Reservation.create(time, trainer1));
    }

    public Reservation createCompletedReservation() {
        Trainer trainer1 = trainerDbFixture.createTrainer1();
        LocalDateTime time = LocalDateTime.of(2025, 12, 18, 10, 0);
        Reservation reservation = Reservation.create(time, trainer1);
        reservation.updateStatus(ReservationStatus.COMPLETE);

        return reservationRepository.save(reservation);
    }
}
