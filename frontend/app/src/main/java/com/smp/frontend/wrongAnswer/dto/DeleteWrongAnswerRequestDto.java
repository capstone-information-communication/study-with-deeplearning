package com.smp.frontend.wrongAnswer.dto;

public class DeleteWrongAnswerRequestDto {
    private long questionId;
    private long workbookId;

    public DeleteWrongAnswerRequestDto(long questionId, long workbookId) {
        this.questionId = questionId;
        this.workbookId = workbookId;
    }
}
