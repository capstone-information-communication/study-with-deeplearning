package core.backend.wrongProblem.wrongWorkbook.domain;

import core.backend.problem.question.domain.Category;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Embeddable
public class WrongFigure {
    @Column(nullable = false)
    private Integer blankCount;

    @Column(nullable = false)
    private Integer multipleCount;

    @Column(nullable = false)
    private Integer shortCount;

    @Column(nullable = false)
    private Integer orderCount;

    public WrongFigure() {
        blankCount = 0;
        multipleCount = 0;
        shortCount = 0;
        orderCount = 0;
    }

    public void addByCategory(Category category, int count) {
        switch (category) {
            case ORDER:
                orderCount += count;
                break;
            case SHORT:
                shortCount += count;
                break;
            case MULTIPLE:
                multipleCount += count;
                break;
            case BLANK:
                blankCount += count;
        }
    }
}
