package core.backend.wrongProblem.wrongWorkbook.domain;

import core.backend.global.domain.BaseTimeEntity;
import core.backend.problem.workbook.domain.WorkbookData;
import core.backend.wrongProblem.wrongQuestion.domain.WrongQuestion;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WrongWorkbook extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "wrongWorkbook")
    List<WrongQuestion> wrongQuestionList = new ArrayList<>();

    //-비즈니스 로직-//
    @Builder
    public WrongWorkbook(WorkbookData workbook, Long memberId) {
        this.memberId = memberId;
        title = workbook.getTitle();
        description = workbook.getDescription();
    }
}
