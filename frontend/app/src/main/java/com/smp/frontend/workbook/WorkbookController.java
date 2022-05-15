package com.smp.frontend.workbook;

import com.smp.frontend.workbook.dto.UploadWorkBookResponseDto;
import com.smp.frontend.workbook.dto.WorkBookResponseDto;
import com.smp.frontend.workbook.dto.WorkBookTestResponse;
import com.smp.frontend.workbook.dto.UploadWorkBookRequestDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WorkbookController {
    @POST("api/v1/workbook-with-text")
    Call<UploadWorkBookResponseDto> SendWorkBook(@Header("Authorization") String token, @Body UploadWorkBookRequestDto request);

    @GET("api/v1/choice/{id}")
    Call<WorkBookTestResponse> getTestApi(@Path("id") Long id);

    @GET("api/v1/choices")
    Call<WorkBookResponseDto> getChoiceList();


    @GET("api/v1/workbooks")
    Call<WorkBookResponseDto> getWorkbook(@Header("Authorization") String token);

}
