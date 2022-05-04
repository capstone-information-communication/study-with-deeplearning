package core.backend.wrongAnswer.dto;

import core.backend.question.domain.Question;
import core.backend.question.dto.QuestionResponseDto;
import core.backend.workbook.domain.Workbook;
import core.backend.workbook.dto.WorkbookInfoResponseDto;
import core.backend.workbook.dto.WorkbookResponseDto;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class WrongAnswerInfoResponseDto {
    private WorkbookInfoResponseDto workbook;
    private List<QuestionResponseDto> questionList;

    public WrongAnswerInfoResponseDto(Workbook workbook, List<Question> questionList) {
        this.workbook = new WorkbookInfoResponseDto(workbook);
        this.questionList = questionList.stream()
                .map(QuestionResponseDto::new)
                .collect(Collectors.toList());
    }
}
