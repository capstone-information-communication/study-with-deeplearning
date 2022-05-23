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

    private WrongFigure(Integer blankCount, Integer multipleCount, Integer shortCount, Integer orderCount) {
        this.blankCount = blankCount;
        this.multipleCount = multipleCount;
        this.shortCount = shortCount;
        this.orderCount = orderCount;
    }

    public WrongFigure convertToWrongFigure() {
        return new WrongFigure(blankCount, multipleCount, shortCount, orderCount);
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
