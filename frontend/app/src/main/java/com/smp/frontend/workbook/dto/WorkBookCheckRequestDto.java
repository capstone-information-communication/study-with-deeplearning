package com.smp.frontend.workbook.dto;

public class WorkBookCheckRequestDto {
    private long questionId;
    private long choiceId;


    public WorkBookCheckRequestDto(long questionId, long choiceId) {
        this.questionId = questionId;
        this.choiceId = choiceId;
    }

    public long getQuestionId() {
        return questionId;
    }

    public long getChoiceId() {
        return choiceId;
    }
}
