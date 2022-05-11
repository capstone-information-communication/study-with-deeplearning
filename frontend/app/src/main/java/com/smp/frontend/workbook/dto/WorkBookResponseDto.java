package com.smp.frontend.workbook.dto;

import java.util.List;

public class WorkBookResponseDto {
    private int count;
    private List<?> data;

    public WorkBookResponseDto(int count, List<?> data) {
        this.count = count;
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public List<?> getData() {
        return data;
    }
}
