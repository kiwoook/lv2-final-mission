package finalmission.member.domain;


import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Embedded
    private Email email;

    @Embedded
    private Password password;

    @Enumerated
    private Roles role;

    @Builder
    public Member(Email email, Password password, Roles role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public static Member create(Email email, Password password) {
        return Member.builder()
                .email(email)
                .password(password)
                .role(Roles.USER)
                .build();
    }
}
