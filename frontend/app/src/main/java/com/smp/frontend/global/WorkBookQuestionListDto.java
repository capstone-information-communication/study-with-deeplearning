package com.smp.frontend.global;

public class WorkBookQuestionListDto {
    public WorkBookQuestionListDto(long questionid, String title, String content,String category) {
        this.questionId = questionid;
        this.title = title;
        this.content = content;
        this.category = category;
    }
    private long questionId;
    private String title;
    private String content;
    private String category;

    public String getCategory() {
        return category;
    }

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
