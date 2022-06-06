package com.smp.frontend.wrongAnswer;

import com.smp.frontend.member.dto.MemberSginInResponseDto;
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
    @GET("api/v1/my/wrong-workbook")
    Call<WrongAnswerResponseDto> WrongAnswerBook(@Header("Authorization") String token);

    @HTTP(method = "DELETE", path = "api/v1/wrong-workbook/{id}",hasBody = false)
    Call<DeleteWrongAnswerResponseDto> DeletWrongAnswer(@Header("Authorization") String token, @Path("id") long id);

    @GET("api/v1/member")
    Call<MemberSginInResponseDto> GetUser(@Header("Authorization") String token);

}
