package finalmission.trainer.domain;

import finalmission.common.exception.InvalidArgumentException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Trainer {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String name;

    private LocalDate birth;

    public Trainer(String name, LocalDate birth) {
        this.name = name;
        this.birth = birth;
        validate();
    }

    public void validate() {
        if (name == null) {
            throw new InvalidArgumentException("이름이 존재해야 합니다.");
        }

        if (birth == null) {
            throw new InvalidArgumentException("생일이 존재해야만 합니다.");
        }
    }
}
