package com.smp.frontend.likeWorkbook.list;

public class likeBookItemData {
    private int id;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    private String title;
    private String description;

    public likeBookItemData(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }
    // 입력받은 숫자의 리스트생성

}
