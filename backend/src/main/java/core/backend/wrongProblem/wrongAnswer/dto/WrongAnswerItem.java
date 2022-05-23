package core.backend.wrongProblem.wrongAnswer.dto;

import core.backend.problem.question.domain.Question;
import lombok.Getter;

@Getter
public class WrongAnswerItem {
    private Long wrongAnswerId;
    private Question question;

    public WrongAnswerItem(Long wrongAnswerId, Question question) {
        this.wrongAnswerId = wrongAnswerId;
        this.question = question;
    }
}
