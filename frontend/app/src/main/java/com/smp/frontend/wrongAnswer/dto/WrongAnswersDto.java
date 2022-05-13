package com.smp.frontend.wrongAnswer.dto;

public class WrongAnswersDto {

    public WrongAnswersDto(String questionId, String questionTitle, String questionContent) {
        QuestionId = questionId;
        QuestionTitle = questionTitle;
        QuestionContent = questionContent;
    }

    private String QuestionId,QuestionTitle,QuestionContent;


}
