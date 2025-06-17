package finalmission.reservation.repository;

import finalmission.reservation.domain.Reservation;
import finalmission.reservation.domain.ReservationStatus;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByStatus(ReservationStatus status);

    @Query("select exists(select r from Reservation r where r.reservationDateTime = :reservationDateTime and r.trainer.id = :trainerId)")
    boolean existsByReservationDateTimeAndTrainerId(LocalDateTime reservationDateTime, Long trainerId);

}
