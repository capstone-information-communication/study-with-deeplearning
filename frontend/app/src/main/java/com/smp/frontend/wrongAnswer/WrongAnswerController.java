package com.smp.frontend.wrongAnswer;

import com.smp.frontend.workbook.dto.UploadWorkBookRequestDto;
import com.smp.frontend.workbook.dto.UploadWorkBookResponseDto;
import com.smp.frontend.workbook.dto.WorkBookTestResponse;
import com.smp.frontend.wrongAnswer.dto.DeleteWrongAnswerRequestDto;
import com.smp.frontend.wrongAnswer.dto.DeleteWrongAnswerResponseDto;
import com.smp.frontend.wrongAnswer.dto.WrongAnswerResponseDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface WrongAnswerController {
    @GET("api/v1/wrong-answer/workbook")
    Call<WrongAnswerResponseDto> WrongAnswerBook(@Header("Authorization") String token);

    @HTTP(method = "DELETE", path = "api/v1/wrong-answer/{id}",hasBody = true)
    Call<DeleteWrongAnswerResponseDto> DeletWrongAnswer(@Header("Authorization") String token, @Body DeleteWrongAnswerRequestDto request, @Path("id") long id);

}
