package com.smp.frontend.workbook.list;

import com.smp.frontend.common.WorkBookQuestionListDto;
import com.smp.frontend.common.choiceListDto;
import com.smp.frontend.common.WrongAnswerQuestionListDto;

import org.json.JSONArray;

import java.util.List;

public class WorkBookQuestionItemData {
    private long id;

    public long getId() {
        return id;
    }

    public WorkBookQuestionListDto getWorkBookQuestionListDto() {
        return WorkBookQuestionListDto;
    }


    private WorkBookQuestionListDto WorkBookQuestionListDto;
    private List<?> choiceList;

    public List<?> getChoiceList() {
        return choiceList;
    }

    public WorkBookQuestionItemData(long workbookId, WorkBookQuestionListDto qeustionClass,List<?> choiceList
/*    ,List<choiceListDto> choice3,List<choiceListDto> choice4*/) {
        this.id = workbookId;
        this.WorkBookQuestionListDto = qeustionClass;
        this.choiceList = choiceList;

/*        this.choice3 = choice3;
        this.choice4 = choice4;*/
    }


}
