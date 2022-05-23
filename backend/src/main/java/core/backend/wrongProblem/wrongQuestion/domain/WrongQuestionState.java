package core.backend.wrongProblem.wrongQuestion.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum WrongQuestionState {
    WRONG("오답 초기 상태"),
    DELETE("오답을 삭제한 상태"),
    RIGHT("오답을 풀어 맞은 상태");

    private String description;
}
