package core.backend.wrongProblem.wrongQuestion.domain;

import core.backend.global.domain.BaseTimeEntity;
import core.backend.problem.choice.domain.ChoiceData;
import core.backend.problem.question.domain.Category;
import core.backend.problem.question.domain.Commentary;
import core.backend.problem.question.domain.QuestionData;
import core.backend.wrongProblem.wrongWorkbook.domain.WrongWorkbook;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WrongQuestion extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String content;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private WrongQuestionState state;

    @Embedded
    private Commentary commentary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wrongWorkbook_id")
    private WrongWorkbook wrongWorkbook;

    @ElementCollection
    @CollectionTable(
            name = "WRONG_CHOICE",
            joinColumns = @JoinColumn(name = "wrongQuestion_id"))
    List<ChoiceData> choiceList = new ArrayList<>();

    //- 연관관계 로직 -//
    private void setWrongWorkbook(WrongWorkbook wrongWorkbook) {
        if (this.wrongWorkbook != null) {
            this.wrongWorkbook.getWrongQuestionList().remove(this);
        }
        this.wrongWorkbook = wrongWorkbook;
        wrongWorkbook.getWrongQuestionList().add(this);
    }

    //- 비즈니스 로직 -//
    @Builder
    public WrongQuestion(QuestionData question, WrongWorkbook wrongWorkbook, List<ChoiceData> choiceList) {
        title = question.getTitle();
        content = question.getContent();
        category = question.getCategory();
        commentary = question.getCommentary();

        state = WrongQuestionState.WRONG;

        this.choiceList.addAll(choiceList);
        setWrongWorkbook(wrongWorkbook);
    }

    public void deleted() {
        this.state = WrongQuestionState.DELETE;
    }

    public void answered() {
        this.state = WrongQuestionState.RIGHT;
    }
}
