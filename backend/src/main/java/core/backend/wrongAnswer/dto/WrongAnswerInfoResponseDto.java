package core.backend.wrongAnswer.dto;

import core.backend.question.domain.Question;
import core.backend.workbook.domain.Workbook;
import core.backend.workbook.dto.WorkbookInfoResponseDto;
import lombok.Getter;

import java.util.List;

@Getter
public class WrongAnswerInfoResponseDto {
    private WorkbookInfoResponseDto workbook;

    public WrongAnswerInfoResponseDto(Workbook workbook, List<Question> questionList) {
        this.workbook = new WorkbookInfoResponseDto(workbook, questionList);
    }
}
