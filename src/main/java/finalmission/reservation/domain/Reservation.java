package finalmission.reservation.domain;


import finalmission.trainer.domain.Trainer;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Reservation {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private LocalDateTime reservationDateTime;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Trainer trainer;

    @Enumerated
    private ReservationStatus status;

    @Builder
    private Reservation(LocalDateTime reservationDateTime, Trainer trainer, ReservationStatus status) {
        this.reservationDateTime = reservationDateTime;
        this.trainer = trainer;
        this.status = status;
    }

    public static Reservation create(LocalDateTime reservationDateTime, Trainer trainer) {
        return Reservation.builder()
                .reservationDateTime(reservationDateTime)
                .trainer(trainer)
                .status(ReservationStatus.AVAILABLE)
                .build();
    }

    public void updateStatus(ReservationStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("status는 null일 수 없습니다.");
        }

        this.status = status;
    }

    public boolean isNotAvailable() {
        return status != ReservationStatus.AVAILABLE;
    }
}
