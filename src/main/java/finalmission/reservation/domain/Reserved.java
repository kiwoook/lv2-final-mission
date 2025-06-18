package finalmission.reservation.domain;

import finalmission.member.domain.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Reserved {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Reservation reservation;

    public Reserved(Member member, Reservation reservation) {
        this.member = member;
        this.reservation = reservation;
        reservation.updateStatus(ReservationStatus.COMPLETE);
    }

    public void updateReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public boolean sameMember(Long memberId) {
        return member.getId().equals(memberId);
    }
}
