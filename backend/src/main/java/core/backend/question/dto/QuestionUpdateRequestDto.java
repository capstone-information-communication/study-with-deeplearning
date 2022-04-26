package core.backend.question.dto;

import core.backend.question.domain.Category;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionUpdateRequestDto {
    private String title;
    private String content;
    private Category category;

    public QuestionUpdateRequestDto(String title, String content, Category category) {
        this.title = title;
        this.content = content;
        this.category = category;
    }
}
