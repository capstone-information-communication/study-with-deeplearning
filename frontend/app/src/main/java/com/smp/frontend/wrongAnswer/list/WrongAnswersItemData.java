package com.smp.frontend.wrongAnswer.list;

import com.smp.frontend.common.choiceListDto;
import com.smp.frontend.common.WrongAnswerQuestionListDto;

import java.util.List;

public class WrongAnswersItemData {


    public List<?> getChoice() {
        return choiceList;
    }

    private List<?> choiceList;
    private WrongAnswerQuestionListDto question;

    public long getWorkbookId() {
        return workbookId;
    }

    private long workbookId;
    public WrongAnswersItemData(long workbookId, WrongAnswerQuestionListDto qeustionClass, List<?> choiceList) {
        this.workbookId = workbookId;
        this.question = qeustionClass;
        this.choiceList = choiceList;
    }


    public WrongAnswerQuestionListDto getQuestion() {
        return question;
    }
}
