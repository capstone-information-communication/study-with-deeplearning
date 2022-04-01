package core.backend.problem.domain;

import core.backend.workbook.entity.Workbook;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Problem {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String question;

    @Column(nullable = false)
    private String answer;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "workbook_id")
    private Workbook workbook;

    //-- 연관관계 메서드 --//
    public void setWorkbook(Workbook workbook) {
        if (this.workbook != null) {
            this.workbook.getProblemList().remove(this);
        }
        this.workbook = workbook;
        workbook.getProblemList().add(this);
    }

    //-- 비즈니스 로직 --//
    @Builder
    public Problem(String question, String answer, Workbook workbook) {
        this.question = question;
        this.answer = answer;
        setWorkbook(workbook);
    }

    public Problem update(String question, String answer) {
        this.question = question;
        this.answer = answer;
        return this;
    }
}
