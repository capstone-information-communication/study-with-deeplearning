package com.smp.frontend.wrongAnswer.list;

import java.util.ArrayList;
import java.util.List;

public class WrongAnswersItemData {
    private long id;

    public long getId() {
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

    public List<String> getChoiceList() {
        return choiceList;
    }

    private List<String> choiceList;

    public WrongAnswersItemData(long id, String title, String content, List<String> choiceList) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.choiceList = choiceList;
    }


}
