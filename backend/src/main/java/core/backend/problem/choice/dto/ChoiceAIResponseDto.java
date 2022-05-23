package core.backend.problem.choice.dto;

import core.backend.problem.choice.domain.Choice;
import core.backend.problem.choice.domain.State;
import core.backend.problem.question.domain.Question;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChoiceAIResponseDto {
    private String content;
    private State state;

    public ChoiceAIResponseDto(String content, State state) {
        this.content = content;
        this.state = state;
    }

    public Choice toEntity(Question question) {
        return Choice.builder()
                .question(question)
                .content(content)
                .state(state)
                .build();
    }
}
