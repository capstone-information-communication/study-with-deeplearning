package com.smp.frontend.workbook.dto;

import java.util.List;

public class WorkBookCheckRequestDto {
    private long workbookId;
    private List<Long> questionIdList;


    public WorkBookCheckRequestDto(long workbookId,  List<Long> questionIdList) {
        this.workbookId = workbookId;
        this.questionIdList = questionIdList;
    }



}
