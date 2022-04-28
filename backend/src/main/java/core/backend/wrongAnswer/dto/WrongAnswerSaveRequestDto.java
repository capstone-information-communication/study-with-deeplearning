package core.backend.wrongAnswer.dto;

import core.backend.wrongAnswer.domain.WrongAnswer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WrongAnswerSaveRequestDto {
    private Long workbookId;
    private Long questionId;

    public WrongAnswerSaveRequestDto(Long workbookId, Long questionId) {
        this.workbookId = workbookId;
        this.questionId = questionId;
    }

    public WrongAnswer toEntity(Long memberId) {
        return WrongAnswer.builder()
                .workbookId(workbookId)
                .questionId(questionId)
                .memberId(memberId)
                .build();
    }
}
