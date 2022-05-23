package core.backend.problem.choice.dto;

import core.backend.problem.choice.domain.Choice;
import core.backend.problem.choice.domain.State;
import core.backend.problem.question.domain.Question;
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
