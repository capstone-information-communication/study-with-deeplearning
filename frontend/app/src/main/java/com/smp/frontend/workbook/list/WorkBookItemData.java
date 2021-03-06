package com.smp.frontend.workbook.list;

import android.graphics.drawable.Drawable;

public class WorkBookItemData {
    private int id;

    public int getLikeCount() {
        return likeCount;
    }

    private int likeCount;
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

    public Drawable getDrawable() {
        return drawable;
    }

    private Drawable drawable;
    public boolean isSearch() {
        return search;
    }

    private boolean search;

    public WorkBookItemData(int id, String title, String description,int likeCount,int page,boolean search,Drawable drawable) {
        this.id = id;
        this.title = title;
        this.likeCount = likeCount;
        this.description = description;
        this.page = page;
        this.search = search;
        this.drawable =drawable;

    }
    // 입력받은 숫자의 리스트생성

}
