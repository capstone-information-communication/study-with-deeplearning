package core.backend.wrongProblem.wrongQuestion.dto;

import core.backend.problem.choice.domain.ChoiceData;
import core.backend.problem.choice.domain.State;
import lombok.Getter;

@Getter
public class WrongChoiceResponseDto {
    private State state;
    private String content;

    public WrongChoiceResponseDto(ChoiceData entity) {
        state = entity.getState();
        content = entity.getContent();
    }
}
