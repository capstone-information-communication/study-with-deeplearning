package com.smp.frontend.wrongAnswer.dto;

import android.widget.ListView;

import java.util.List;

public class WrongAnswerResponseDto {
    int count;


    public int getCount() {
        return count;
    }

    public List<?> getData() {
        return data;
    }

    public List<?> getWorkbook() {
        return workbook;
    }

    List<?> workbook;
    List<?> data;


}
