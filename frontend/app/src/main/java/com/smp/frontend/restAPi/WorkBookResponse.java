package com.smp.frontend.restAPi;

import java.util.List;

public class WorkBookResponse {
    private int count;
    private List<?> data;

    public WorkBookResponse(int count, List<?> data) {
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
