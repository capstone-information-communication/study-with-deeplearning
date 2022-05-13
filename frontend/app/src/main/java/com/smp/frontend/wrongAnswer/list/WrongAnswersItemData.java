package com.smp.frontend.wrongAnswer.list;

import java.util.ArrayList;

public class WrongAnswersItemData {
    private String id;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    private String title;
    private String content;

    public String getChoiceList() {
        return choiceList;
    }

    private String choiceList;

    public WrongAnswersItemData(String id, String title, String content, String choiceList) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.choiceList = choiceList;
    }

    // 입력받은 숫자의 리스트생성
    public static ArrayList<WrongAnswersItemData> createContactsList(int count, String id, String title, String description,String choiceList) {
        ArrayList<WrongAnswersItemData> contacts = new ArrayList<WrongAnswersItemData>();

        for (int i = 1; i <= count; i++) {
            contacts.add(new WrongAnswersItemData(id,title,description,choiceList));
        }
        return contacts;
    }
}
