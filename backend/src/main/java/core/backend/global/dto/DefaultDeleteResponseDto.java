package core.backend.global.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DefaultDeleteResponseDto {
    private String message;

    @Builder
    public DefaultDeleteResponseDto(String message) {
        this.message = message;
    }
}
