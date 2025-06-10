package finalmission.reservation.repository;

import finalmission.reservation.domain.Reserved;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservedRepository extends JpaRepository<Reserved, Long> {
}
