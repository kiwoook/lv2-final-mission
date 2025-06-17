package finalmission.fixture.db;

import finalmission.reservation.domain.Reservation;
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

    public Reservation creatReservation() {
        Trainer trainer1 = trainerDbFixture.createTrainer1();

        return reservationRepository.save(Reservation.create(LocalDateTime.now(), trainer1));
    }
}
