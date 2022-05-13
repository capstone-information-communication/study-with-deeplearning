package com.smp.frontend.wrongAnswer.list;

import java.util.ArrayList;

public class WrongAnswerBookItemData {
    private String id;

    public String getId() {
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

    public WrongAnswerBookItemData(String id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }
    // 입력받은 숫자의 리스트생성
    public static ArrayList<WrongAnswerBookItemData> createContactsList(int count, String id, String title, String description) {
        ArrayList<WrongAnswerBookItemData> contacts = new ArrayList<WrongAnswerBookItemData>();

        for (int i = 1; i <= count; i++) {
            contacts.add(new WrongAnswerBookItemData(id,title,description));
        }
        return contacts;
    }
}
