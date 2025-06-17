package finalmission.member.repository;

import finalmission.member.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select M from Member M where M.email.email = :email and M.password.password = :password")
    Optional<Member> findByEmailAndPassword(String email, String password);
}
