package com.smp.frontend.global;

public class WrongAnswerQuestionListDto {
    public WrongAnswerQuestionListDto(long questionid, long wrongAnswerId , String title, String content, String comment, String category) {
        this.questionId = questionid;
        this.wrongAnswerId = wrongAnswerId;
        this.title = title;
        this.content = content;
        this.comment = comment;
        this.category = category;
    }



    private long wrongAnswerId;
    private String title;
    private String content;
    private String comment;
    private long questionId;
    private String category;

    public long getWrongAnswerId() {
        return wrongAnswerId;
    }
    public String getTitle() {
        return title;
    }
    public String getContent() {
        return content;
    }
    public String getComment() {
        return comment;
    }
    public long getQuestionId() {
        return questionId;
    }
    public String getCategory() { return category; }



}
