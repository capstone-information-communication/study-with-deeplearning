package com.smp.frontend.wrongAnswer.dto;

import org.json.JSONObject;

import java.util.List;


public class WrongAnswerTestResponse {
    private long id;
    private String state;
    private String content;
    private String title;
    private String updatedAt;
    private String createdAt;
    private String description;
    private Object commentary;
    private long questionId;

    public long getQuestionId() {
        return questionId;
    }

    public long getWrongAnswerId() {
        return wrongAnswerId;
    }

    private long wrongAnswerId;

    public String getComment() {
        return comment;
    }

    private String comment;


    public Object getCommentary() {
        return commentary;
    }

    public List<?> getWorkbook() {
        return workbook;
    }

    private List<?> workbook;

    public List<?> getChoiceList() {
        return choiceList;
    }

    private List<?> choiceList;

    public List<?> getWrongAnswerQuestionList() {
        return wrongAnswerQuestionList;
    }

    private List<?> wrongAnswerQuestionList;

    public String getTitle() { return title; }

    public String getDescription() { return description; }

    public long getId() {
        return id;
    }

    public String getState() {
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
