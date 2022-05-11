package core.backend.choice.dto;

import core.backend.choice.domain.Choice;
import core.backend.choice.domain.State;
import core.backend.question.domain.Question;
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
