package com.smp.frontend.global;

public class choiceListDto {
    public long getId() {
        return id;
    }

    public String getState() {
        return state;
    }

    public String getContent() {
        return content;
    }

    private long id;
    private String state;
    private String content;

    public choiceListDto(long id, String state, String content) {
        this.id = id;
        this.state = state;
        this.content = content;
    }
}
