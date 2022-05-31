package com.smp.frontend.workbook.dto;

import java.util.List;

public class WorkBookCheckRequestDto {
    private long id;
    private List<?> questionIdList;


    public WorkBookCheckRequestDto(long id,  List<?> questionIdList) {
        this.id = id;
        this.questionIdList = questionIdList;
    }



}
