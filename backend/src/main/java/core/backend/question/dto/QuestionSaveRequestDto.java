package core.backend.question.dto;

import core.backend.commentary.domain.Commentary;
import core.backend.question.domain.Category;
import core.backend.question.domain.Question;
import core.backend.workbook.domain.Workbook;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionSaveRequestDto {
    private Long commentaryId;
    private Long workbookId;
    private String title;
    private String content;
    private Category category;

    public QuestionSaveRequestDto(Long commentaryId, Long workbookId, String title, String content, Category category) {
        this.commentaryId = commentaryId;
        this.workbookId = workbookId;
        this.title = title;
        this.content = content;
        this.category = category;
    }

    public Question toEntity(Commentary commentary, Workbook workbook) {
        return Question.builder()
                .title(title)
                .category(category)
                .content(content)
                .commentary(commentary)
                .workbook(workbook)
                .build();
    }
}