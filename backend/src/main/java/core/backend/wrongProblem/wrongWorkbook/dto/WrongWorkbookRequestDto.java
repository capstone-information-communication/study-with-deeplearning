package core.backend.wrongProblem.wrongWorkbook.dto;

import core.backend.problem.workbook.domain.Workbook;
import core.backend.problem.workbook.domain.WorkbookData;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class WrongWorkbookRequestDto {
    private Long id;
    private List<Long> questionIdList;

    public WrongWorkbookRequestDto(Long id, List<Long> questionIdList) {
        this.id = id;
        this.questionIdList = questionIdList;
    }

    public WorkbookData toWorkbookData(Workbook workbook) {
        return new WorkbookData(workbook);
    }
}
