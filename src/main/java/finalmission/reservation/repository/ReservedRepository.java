package finalmission.reservation.repository;

import finalmission.member.domain.Member;
import finalmission.reservation.domain.Reservation;
import finalmission.reservation.domain.Reserved;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservedRepository extends JpaRepository<Reserved, Long> {

    Optional<Reserved> findByReservation_IdAndMember_Id(Long reservationId, Long memberId);

    Optional<Reserved> findByReservationAndMember(Reservation reservation, Member member);

}
