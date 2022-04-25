package core.backend.wrongAnswer.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WrongAnswer {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private Long workbookId;

    @Column(nullable = false)
    private Long questionId;

    //-- 비즈니스 로직 --//
    @Builder
    public WrongAnswer(Long memberId, Long workbookId, Long questionId) {
        this.memberId = memberId;
        this.workbookId = workbookId;
        this.questionId = questionId;
    }
}