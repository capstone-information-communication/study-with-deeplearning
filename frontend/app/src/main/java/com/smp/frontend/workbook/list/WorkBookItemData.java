package com.smp.frontend.workbook.list;

public class WorkBookItemData {
    private int id;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getdescription() {
        return description;
    }

    private String title;
    private String description;

    public WorkBookItemData(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }
    // 입력받은 숫자의 리스트생성

}
