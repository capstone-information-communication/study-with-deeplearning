package core.backend.choice.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum State {
    ANSWER("정답인 보기"),
    WRONG("오답인 보기");

    private final String description;
}