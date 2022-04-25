package core.backend.commentary.dto;

import core.backend.commentary.domain.Commentary;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentaryResponseDto {
    private Long id;
    private String content;

    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;

    public CommentaryResponseDto(Commentary entity) {
        id = entity.getId();
        content = entity.getContent();

        updatedAt = entity.getUpdatedAt();
        createdAt = entity.getCreatedAt();
    }
}
