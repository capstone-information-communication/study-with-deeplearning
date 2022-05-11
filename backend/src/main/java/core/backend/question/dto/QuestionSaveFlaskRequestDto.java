package core.backend.question.dto;

import lombok.Getter;

@Getter
public class QuestionSaveFlaskRequestDto {
    private String text;

    public QuestionSaveFlaskRequestDto(String text) {
        this.text = text;
    }
}
