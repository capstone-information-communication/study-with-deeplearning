package core.backend.wrongAnswer.dto;

import core.backend.workbook.domain.Workbook;
import core.backend.workbook.dto.WorkbookResponseDto;
import lombok.Getter;

import java.util.List;

@Getter
public class WrongAnswerInfoResponseDto {
    private WorkbookResponseDto workbook;
    private List<Long> questionIdList;

    public WrongAnswerInfoResponseDto(Workbook workbook, List<Long> questionIdList) {
        this.workbook = new WorkbookResponseDto(workbook);
        this.questionIdList = questionIdList;
    }
}
