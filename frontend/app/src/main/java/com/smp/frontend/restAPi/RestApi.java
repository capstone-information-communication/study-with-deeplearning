package com.smp.frontend.restAPi;

import java.time.LocalDateTime;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RestApi {

    @GET("api/v1/choice/{id}")
    Call<TestApi> getTestApi(@Path("id") Long id);



    @GET("api/v1/choices")
    Call<TestDataApi> getChoiceList();
}
