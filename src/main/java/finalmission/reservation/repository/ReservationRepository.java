package finalmission.reservation.repository;

import finalmission.reservation.domain.Reservation;
import finalmission.reservation.domain.ReservationStatus;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByStatus(ReservationStatus status);

    // JPQL 다시 공부해야함
    boolean existsByReservationDateTimeAndTrainer_Id(LocalDateTime reservationDateTime, Long trainerId);

    boolean existsByReservationDateTimeAndTrainer_IdAndStatus(LocalDateTime reservationDateTime, Long trainerId,
                                                              ReservationStatus status);

}
