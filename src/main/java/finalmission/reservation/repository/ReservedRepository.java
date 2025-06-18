package finalmission.reservation.repository;

import finalmission.member.domain.Member;
import finalmission.reservation.domain.Reservation;
import finalmission.reservation.domain.Reserved;
import finalmission.reservation.dto.ReservedDto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservedRepository extends JpaRepository<Reserved, Long> {

    Optional<Reserved> findByReservationAndMember(Reservation reservation, Member member);

    @Query(""" 
            select new finalmission.reservation.dto.ReservedDto(rd.id, r)
                        from Reserved rd join rd.reservation r
                                    where rd.member.id = :memberId
            """)
    List<ReservedDto> findReservedByMember(Long memberId);
}
