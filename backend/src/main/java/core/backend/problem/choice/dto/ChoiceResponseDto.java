package core.backend.problem.choice.dto;

import core.backend.problem.choice.domain.Choice;
import core.backend.problem.choice.domain.State;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ChoiceResponseDto {
    private Long id;
    private State state;
    private String content;

    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;

    @Builder
    public ChoiceResponseDto(Choice entity) {
        id = entity.getId();
        state = entity.getState();
        content = entity.getContent();

        updatedAt = entity.getUpdatedAt();
        createdAt = entity.getCreatedAt();
    }
}