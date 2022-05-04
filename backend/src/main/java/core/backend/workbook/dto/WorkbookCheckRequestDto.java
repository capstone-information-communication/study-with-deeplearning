package core.backend.workbook.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkbookCheckRequestDto {
    private Long questionId;
    private Long choiceId;

    public WorkbookCheckRequestDto(Long questionId, Long choiceId) {
        this.questionId = questionId;
        this.choiceId = choiceId;
    }
}
