package core.backend.wrongAnswer.dto;

import core.backend.choice.dto.ChoiceResponseDto;
import core.backend.question.domain.Category;
import core.backend.question.domain.Commentary;
import core.backend.question.domain.Question;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class WrongAnswerQuestionResponseDto {
    private Long questionId;
    private Long wrongAnswerId;
    private String title;
    private String content;
    private Category category;

    private Commentary commentary;
    private List<ChoiceResponseDto> choiceList;

    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;

    public WrongAnswerQuestionResponseDto(Question entity, Long wrongAnswerId) {
        questionId = entity.getId();
        this.wrongAnswerId = wrongAnswerId;
        title = entity.getTitle();
        content = entity.getContent();
        category = entity.getCategory();
        commentary = entity.getCommentary();

        choiceList = entity.getChoiceList().stream()
                .map(ChoiceResponseDto::new)
                .collect(Collectors.toList());

        updatedAt = entity.getUpdatedAt();
        createdAt = entity.getCreatedAt();
    }
}
