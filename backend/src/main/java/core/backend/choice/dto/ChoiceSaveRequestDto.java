package core.backend.choice.dto;

import core.backend.choice.domain.Choice;
import core.backend.choice.domain.State;
import core.backend.question.domain.Question;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChoiceSaveRequestDto {
    private Long questionId;
    private State state;
    private String content;

    public ChoiceSaveRequestDto(Long questionId, State state, String content) {
        this.questionId = questionId;
        this.state = state;
        this.content = content;
    }

    public Choice toEntity(Question question) {
        return Choice.builder()
                .state(state)
                .content(content)
                .question(question)
                .build();
    }
}
