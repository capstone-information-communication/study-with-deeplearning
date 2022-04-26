package core.backend.commentary.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentaryUpdateRequestDto {
    private String content;

    public CommentaryUpdateRequestDto(String content) {
        this.content = content;
    }
}
