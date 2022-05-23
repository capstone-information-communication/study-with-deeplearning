package core.backend.wrongProblem.wrongAnswer.dto;

import core.backend.wrongProblem.wrongAnswer.domain.WrongAnswer;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class WrongAnswerResponseDto {
    private Long id;
    private Long workbookId;
    private Long questionId;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;

    public WrongAnswerResponseDto(WrongAnswer entity) {
        id = entity.getId();
        workbookId = entity.getWorkbookId();
        questionId = entity.getQuestionId();
        updatedAt = entity.getUpdatedAt();
        createdAt = entity.getCreatedAt();
    }
}
