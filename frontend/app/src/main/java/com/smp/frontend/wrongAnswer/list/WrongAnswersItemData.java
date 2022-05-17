package com.smp.frontend.wrongAnswer.list;

import com.smp.frontend.common.choiceListDto;
import com.smp.frontend.common.questionListDto;

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
    private choiceListDto choice;
    private questionListDto question;

    public WrongAnswersItemData(questionListDto qeustionClass, choiceListDto ChoiceClass) {
        this.choice = ChoiceClass;
        this.question = qeustionClass;
    }


    public choiceListDto getChoice() {
        return choice;
    }

    public questionListDto getQuestion() {
        return question;
    }
}
