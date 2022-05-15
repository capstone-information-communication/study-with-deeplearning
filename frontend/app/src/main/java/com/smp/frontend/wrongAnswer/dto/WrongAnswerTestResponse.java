package com.smp.frontend.wrongAnswer.dto;

import java.util.List;

enum State {
    ANSWER, WRONG;
}

public class WrongAnswerTestResponse {
    private long id;
    private State state;
    private String content;
    private String title;
    private String updatedAt;
    private String createdAt;
    private String description;

    public List<?> getWorkbook() {
        return workbook;
    }

    private List<?> workbook;

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

    public State getState() {
        return state;
    }

    public String getContent() {
        return content;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
