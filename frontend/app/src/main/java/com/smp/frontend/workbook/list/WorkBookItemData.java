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

    public int getPage() {
        return page;
    }

    private int page;

    private String title;
    private String description;

    public boolean isSearch() {
        return search;
    }

    private boolean search;

    public WorkBookItemData(int id, String title, String description,int page,boolean search) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.page = page;
        this.search = search;
    }
    // 입력받은 숫자의 리스트생성

}
