package core.backend.problem.choice.dto;

import core.backend.problem.choice.domain.State;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ChoiceUpdateRequestDto {
    private State state;
    private String content;

    public ChoiceUpdateRequestDto(State state, String content) {
        this.state = state;
        this.content = content;
    }
}
