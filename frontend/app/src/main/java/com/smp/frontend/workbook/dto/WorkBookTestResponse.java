package com.smp.frontend.workbook.dto;

import java.util.List;

public class WorkBookTestResponse {
    private long id;
    private String state;
    private String content;
    private String title;
    private String updatedAt;
    private String createdAt;
    private String description;
    private int likeCount;

    public int getLikeCount() {
        return likeCount;
    }

    public List<?> getChoiceList() {
        return choiceList;
    }

    private List<?> choiceList;

    public List<?> getQuestionList() {
        return questionList;
    }

    private List<?> questionList;

    public String getTitle() { return title; }

    public String getDescription() { return description; }

    public long getId() {
        return id;
    }

    public String getState() { return state;}

    public String getContent() {
        return content;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    private String answer;
    private String wrong;
    private String category;

    public String getCategory() {
        return category;
    }

    public String getAnswer() {
        return answer;
    }

    public String getWrong() {
        return wrong;
    }
}
