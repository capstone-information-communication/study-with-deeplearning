package com.smp.frontend.wrongAnswer.list;

import java.util.ArrayList;

public class WrongAnswerBookItemData {
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
    private String likecount;

    public WrongAnswerBookItemData(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }
    // 입력받은 숫자의 리스트생성

}
