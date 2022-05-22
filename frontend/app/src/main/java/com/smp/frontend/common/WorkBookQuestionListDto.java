package com.smp.frontend.common;

public class WorkBookQuestionListDto {
    public WorkBookQuestionListDto(long questionid, String title, String content) {
        this.questionId = questionid;
        this.title = title;
        this.content = content;
    }
    private long questionId;
    private String title;
    private String content;
    public long getQuestionId() {
        return questionId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }


}
