package core.backend.problem.dto;

import core.backend.problem.domain.Problem;
import core.backend.workbook.entity.Workbook;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProblemSaveRequestDto {
    private Long workbookId;
    private String question;
    private String answer;

    public ProblemSaveRequestDto(Long workbookId, String question, String answer) {
        this.workbookId = workbookId;
        this.question = question;
        this.answer = answer;
    }

    public Problem toEntity(Workbook workbook) {
        return Problem.builder()
                .workbook(workbook)
                .question(question)
                .answer(answer)
                .build();
    }
}
