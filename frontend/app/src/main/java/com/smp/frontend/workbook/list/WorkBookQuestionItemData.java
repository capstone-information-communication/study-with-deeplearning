package com.smp.frontend.workbook.list;

import com.smp.frontend.common.WorkBookQuestionListDto;
import com.smp.frontend.common.choiceListDto;
import com.smp.frontend.common.WrongAnswerQuestionListDto;

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

    private List<choiceListDto> choice2;
    private List<choiceListDto> choice1;
/*    private List<choiceListDto> choice3;
    private List<choiceListDto> choice4;

    public List<choiceListDto> getChoice3() {
        return choice3;
    }

    public List<choiceListDto> getChoice4() {
        return choice4;
    }*/

    public List<choiceListDto> getChoice1() {
        return choice1;
    }

    public List<choiceListDto> getChoice2() {
        return choice2;
    }

    public WorkBookQuestionItemData(long workbookId, WorkBookQuestionListDto qeustionClass, List<choiceListDto> choice1 , List<choiceListDto> choice2
/*    ,List<choiceListDto> choice3,List<choiceListDto> choice4*/) {
        this.id = workbookId;
        this.WorkBookQuestionListDto = qeustionClass;
        this.choice1 = choice1;
        this.choice2 = choice2;
/*        this.choice3 = choice3;
        this.choice4 = choice4;*/
    }


}
