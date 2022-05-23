package core.backend.problem.question.dto;

import core.backend.problem.choice.dto.ChoiceResponseDto;
import core.backend.problem.question.domain.Category;
import core.backend.problem.question.domain.Commentary;
import core.backend.problem.question.domain.Question;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class QuestionResponseDto {
    private Long id;
    private String title;
    private String content;
    private Category category;

    private Commentary commentary;
    private List<ChoiceResponseDto> choiceList;

    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;

    @Builder
    public QuestionResponseDto(Question entity) {
        id = entity.getId();
        title = entity.getTitle();
        content = entity.getContent();
        category = entity.getCategory();
        commentary = entity.getCommentary();

        choiceList = entity.getChoiceList()
                .stream()
                .map(ChoiceResponseDto::new)
                .collect(Collectors.toList());

        updatedAt = entity.getUpdatedAt();
        createdAt = entity.getCreatedAt();
    }
}
