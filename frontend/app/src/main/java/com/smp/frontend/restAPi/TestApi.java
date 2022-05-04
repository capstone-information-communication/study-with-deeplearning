package com.smp.frontend.restAPi;

enum State {
    ANSWER, WRONG;
}

public class TestApi {
    private long id;
    private State state;
    private String content;

    private String updatedAt;
    private String createdAt;

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
