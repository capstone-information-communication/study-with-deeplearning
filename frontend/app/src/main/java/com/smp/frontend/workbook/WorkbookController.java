package com.smp.frontend.workbook;

import com.smp.frontend.workbook.dto.UploadWorkBookResponseDto;
import com.smp.frontend.workbook.dto.WorkBookCheckRequestDto;
import com.smp.frontend.workbook.dto.WorkBookResponseDto;
import com.smp.frontend.workbook.dto.WorkBookTestResponse;
import com.smp.frontend.workbook.dto.UploadWorkBookRequestDto;
import com.smp.frontend.workbook.dto.likeWorkBook;
import com.smp.frontend.wrongAnswer.dto.DeleteWrongAnswerRequestDto;
import com.smp.frontend.wrongAnswer.dto.DeleteWrongAnswerResponseDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WorkbookController {
    @POST("api/v1/workbook-with-text")
    Call<UploadWorkBookResponseDto> SendWorkBook(@Header("Authorization") String token, @Body UploadWorkBookRequestDto request);

    @GET("api/v1/choices")
    Call<WorkBookResponseDto> getChoiceList();

    @GET("api/v1/workbooks")
    Call<WorkBookResponseDto> getWorkbook(@Header("Authorization") String token, @Query("page") int page);

    @GET("api/v1/workbook/search")
    Call<WorkBookResponseDto> getWorkBookSearch(@Header("Authorization") String token,@Query("title") String title,@Query("description") String description ,@Query("page") int page);

    @POST("api/v1/wrong-workbook")
    Call<WorkBookTestResponse> WorkBookCheck(@Header("Authorization") String token,@Body WorkBookCheckRequestDto requestDto );

    @DELETE("api/v1/workbook/{id}")
    Call<WorkBookResponseDto> DeleteWorkBook(@Header("Authorization") String token, @Path("id") long id);

    @POST("api/v1/like-workbook")
    Call<WorkBookTestResponse> likeWorkBook(@Header("Authorization") String token, @Body likeWorkBook workBookId);

    @DELETE("api/v1/like-workbook/{id}")
    Call<WorkBookTestResponse> deleteLikeWorkBook(@Header("Authorization") String token, @Path("id") long id);


}
