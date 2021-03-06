package core.backend.problem.question.dto;

import core.backend.problem.question.domain.Category;
import core.backend.problem.question.domain.Commentary;
import core.backend.problem.question.domain.Question;
import core.backend.problem.workbook.domain.Workbook;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionSaveRequestDto {
    private Long workbookId;
    private String title;
    private String content;
    private Category category;
    private Commentary commentary;

    public QuestionSaveRequestDto(Long workbookId, String title, String content, Category category, Commentary commentary) {
        this.workbookId = workbookId;
        this.title = title;
        this.content = content;
        this.category = category;
        this.commentary = commentary;
    }

    public Question toEntity(Workbook workbook) {
        return Question.builder()
                .title(title)
                .category(category)
                .content(content)
                .commentary(commentary)
                .workbook(workbook)
                .build();
    }
}