package core.backend.problem.workbook.domain;

import core.backend.problem.question.domain.Question;
import lombok.Getter;

import java.util.List;

@Getter
public class WorkbookData {
    private String title;
    private String description;
    public WorkbookData(Workbook entity) {
        title = entity.getTitle();
        description = entity.getDescription();
    }
}
