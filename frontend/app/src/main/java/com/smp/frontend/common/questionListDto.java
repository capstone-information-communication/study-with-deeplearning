package com.smp.frontend.common;

public class questionListDto {
    public questionListDto(long questionid,long wrongAnswerId ,String title, String content,String comment) {
        this.questionId = questionid;
        this.wrongAnswerId = wrongAnswerId;
        this.title = title;
        this.content = content;
        this.comment = comment;
    }

    public long getWrongAnswerId() {
        return wrongAnswerId;
    }

    private long wrongAnswerId;
    public String getComment() {
        return comment;
    }

    private String comment;

    private long questionId;

    public long getQuestionId() {
        return questionId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    private String title;
    private String content;
}
