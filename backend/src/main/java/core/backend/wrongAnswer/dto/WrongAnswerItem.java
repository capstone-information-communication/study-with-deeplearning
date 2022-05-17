package core.backend.wrongAnswer.dto;

import core.backend.question.domain.Question;
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
