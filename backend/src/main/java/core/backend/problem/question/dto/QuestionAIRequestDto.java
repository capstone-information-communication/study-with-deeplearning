package core.backend.problem.question.dto;

import lombok.Getter;

@Getter
public class QuestionAIRequestDto {
    private String text;

    public QuestionAIRequestDto(String text) {
        this.text = text;
    }
}
