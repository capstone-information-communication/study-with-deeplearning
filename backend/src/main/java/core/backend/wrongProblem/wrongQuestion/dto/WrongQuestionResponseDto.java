package core.backend.wrongProblem.wrongQuestion.dto;

import core.backend.problem.question.domain.Category;
import core.backend.problem.question.domain.Commentary;
import core.backend.wrongProblem.wrongQuestion.domain.WrongQuestion;
import core.backend.wrongProblem.wrongQuestion.domain.WrongQuestionState;
import lombok.Getter;

import javax.xml.stream.events.Comment;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class WrongQuestionResponseDto {
    private Long id;
    private String title;
    private String content;
    private Category category;
    private Commentary commentary;
    private WrongQuestionState state;
    private List<WrongChoiceResponseDto> choiceList;

    public WrongQuestionResponseDto(WrongQuestion entity) {
        id = entity.getId();
        title = entity.getTitle();
        content = entity.getContent();
        category = entity.getCategory();
        commentary = entity.getCommentary();
        state = entity.getState();
        choiceList = entity.getChoiceList().stream()
                .map(WrongChoiceResponseDto::new)
                .collect(Collectors.toList());
    }
}
