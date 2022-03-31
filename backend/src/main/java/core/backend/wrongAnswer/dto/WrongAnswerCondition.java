package core.backend.wrongAnswer.dto;

import lombok.Data;

@Data
public class WrongAnswerCondition {
    private Long memberId;
    private Long problemId;
    private Long workbookId;
}
