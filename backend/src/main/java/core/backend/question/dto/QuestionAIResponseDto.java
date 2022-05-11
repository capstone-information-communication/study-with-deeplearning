package core.backend.question.dto;

import core.backend.choice.dto.ChoiceAIResponseDto;
import core.backend.question.domain.Category;
import core.backend.question.domain.Commentary;
import core.backend.question.domain.Question;
import core.backend.workbook.domain.Workbook;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionAIResponseDto {
    private String title;
    private String content;
    private Category category;
    private Commentary commentary;
    private List<ChoiceAIResponseDto> choiceList;

    public QuestionAIResponseDto(String title, String content, Category category, Commentary commentary, List<ChoiceAIResponseDto> choiceList) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.commentary = commentary;
        this.choiceList = choiceList;
    }

    public Question toEntity(Workbook workbook) {
        return Question.builder()
                .workbook(workbook)
                .title(title)
                .content(content)
                .category(category)
                .commentary(commentary)
                .build();
    }
}
