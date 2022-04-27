package core.backend.question.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Commentary {
    private String comment;

    @Builder
    public Commentary(String comment) {
        this.comment = comment;
    }
}
