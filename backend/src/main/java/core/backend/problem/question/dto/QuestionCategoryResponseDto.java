package core.backend.problem.question.dto;

import core.backend.problem.question.domain.Category;
import core.backend.problem.question.domain.Question;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class QuestionCategoryResponseDto {
    private Category category;
    private List<QuestionResponseDto> questionList;

    public QuestionCategoryResponseDto(Category category, List<Question> entityList) {
        this.category = category;
        questionList = entityList.stream()
                .map(QuestionResponseDto::new)
                .collect(Collectors.toList());
    }
}
