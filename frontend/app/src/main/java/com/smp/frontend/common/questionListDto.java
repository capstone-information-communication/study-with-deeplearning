package com.smp.frontend.common;

public class questionListDto {
    public questionListDto(long id, String title, String content,String comment) {
        Id = id;
        this.title = title;
        this.content = content;
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    private String comment;

    private long Id;

    public long getId() {
        return Id;
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
