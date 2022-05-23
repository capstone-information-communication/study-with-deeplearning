package core.backend.problem.question.dto;

import core.backend.problem.question.domain.Category;
import core.backend.problem.question.domain.Commentary;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionUpdateRequestDto {
    private String title;
    private String content;
    private Category category;
    private Commentary commentary;

    public QuestionUpdateRequestDto(String title, String content, Category category, Commentary commentary) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.commentary = commentary;
    }
}
