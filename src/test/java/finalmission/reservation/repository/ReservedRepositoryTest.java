package finalmission.reservation.repository;

import static org.assertj.core.api.Assertions.assertThat;

import finalmission.fixture.db.MemberDbFixture;
import finalmission.fixture.db.ReservationDbFixture;
import finalmission.fixture.db.TrainerDbFixture;
import finalmission.member.domain.Member;
import finalmission.reservation.domain.Reservation;
import finalmission.reservation.domain.Reserved;
import finalmission.reservation.dto.ReservedDto;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import({ReservationDbFixture.class, TrainerDbFixture.class, MemberDbFixture.class})
class ReservedRepositoryTest {

    @Autowired
    private ReservedRepository reservedRepository;

    @Autowired
    private ReservationDbFixture reservationDbFixture;

    @Autowired
    private MemberDbFixture memberDbFixture;

    @DisplayName("나의 예약을 모두 조회한다")
    @Test
    void findMyReservedTest1() {
        // given
        Member user = memberDbFixture.createUser();
        Reservation reservation = reservationDbFixture.creatReservation1();

        Reserved reserved1 = new Reserved(user, reservation);
        Reserved reserved2 = new Reserved(user, reservation);
        Reserved reserved3 = new Reserved(user, reservation);

        reservedRepository.saveAll(List.of(reserved1, reserved2, reserved3));
        // when
        List<ReservedDto> result = reservedRepository.findReservedByMember(user.getId());

        // then
        assertThat(result).hasSize(3);
    }
}