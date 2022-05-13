package com.smp.frontend.wrongAnswer;

import com.smp.frontend.wrongAnswer.dto.WrongAnswerResponseDto;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface WrongAnswerController {
    @GET("api/v1/wrong-answer/workbook")
    Call<WrongAnswerResponseDto> WrongAnswerBook(@Header("Authorization") String token);
}
