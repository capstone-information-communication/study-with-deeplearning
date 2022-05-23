package core.backend.problem.question.domain;

import core.backend.problem.choice.domain.Choice;
import core.backend.problem.workbook.domain.Workbook;
import lombok.Getter;

import java.util.List;

@Getter
public class QuestionData {
    private String title;
    private String content;
    private Category category;
    private Commentary commentary;
    private Workbook workbook;
    private List<Choice> choiceList;

    public QuestionData(Question entity) {
        title = entity.getTitle();
        content = entity.getContent();
        category = entity.getCategory();
        commentary = entity.getCommentary();
        workbook = entity.getWorkbook();
        choiceList = entity.getChoiceList();
    }
}
