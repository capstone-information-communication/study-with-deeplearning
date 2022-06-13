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

    public int getPage() {
        return page;
    }

    private int page;

    public likeBookItemData(int id, String title, String description,int page) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.page = page;
    }
    // 입력받은 숫자의 리스트생성

}
