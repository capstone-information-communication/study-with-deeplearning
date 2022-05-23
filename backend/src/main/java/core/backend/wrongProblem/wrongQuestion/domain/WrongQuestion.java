package core.backend.wrongProblem.wrongQuestion.domain;

import core.backend.global.domain.BaseTimeEntity;
import core.backend.problem.question.domain.Category;
import core.backend.problem.question.domain.Commentary;
import core.backend.wrongProblem.wrongWorkbook.domain.WrongWorkbook;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WrongQuestion extends BaseTimeEntity {
    @Id @GeneratedValue
    private Long id;

    private String title;

    private String content;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Embedded
    private Commentary commentary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wrongWorkbook_id")
    private WrongWorkbook wrongWorkbook;
}
