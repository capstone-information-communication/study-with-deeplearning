package com.smp.frontend.wrongAnswer.list;

import com.smp.frontend.common.choiceListDto;
import com.smp.frontend.common.questionListDto;

import java.util.HashMap;
import java.util.List;

public class WrongAnswersItemData {
/*    private long id;

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    private String title;
    private String content;

    public List<String> getChoiceList() {
        return choiceList;
    }

    private List<String> choiceList;*/


    private List<choiceListDto> choice1;

    public List<choiceListDto> getChoice1() {
        return choice1;
    }

    public List<choiceListDto> getChoice2() {
        return choice2;
    }

    private List<choiceListDto> choice2;
    private questionListDto question;

    public long getWorkbookId() {
        return workbookId;
    }

    private long workbookId;
    public WrongAnswersItemData(long workbookId, questionListDto qeustionClass, List<choiceListDto> ChoiceClass1, List<choiceListDto> ChoiceClass2) {
        this.workbookId = workbookId;
        this.choice1 = ChoiceClass1;
        this.choice2 =ChoiceClass2;
        this.question = qeustionClass;
    }


    public questionListDto getQuestion() {
        return question;
    }
}
