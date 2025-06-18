package finalmission.fixture.db;

import finalmission.member.domain.Member;
import finalmission.reservation.domain.Reservation;
import finalmission.reservation.domain.Reserved;
import finalmission.reservation.repository.ReservationRepository;
import finalmission.reservation.repository.ReservedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReservedDbFixture {

    @Autowired
    private ReservationDbFixture reservationDbFixture;

    @Autowired
    private MemberDbFixture memberDbFixture;

    @Autowired
    private ReservedRepository reservedRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    public Reserved createReserved() {
        Reservation completedReservation = reservationDbFixture.createCompletedReservation();
        Member user = memberDbFixture.createUser();
        Reserved reserved = new Reserved(user, completedReservation);

        return reservedRepository.save(reserved);
    }

}
