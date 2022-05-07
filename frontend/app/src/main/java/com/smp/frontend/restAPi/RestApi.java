package com.smp.frontend.restAPi;

import com.smp.frontend.PreferencesManager;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RestApi {


    @GET("api/v1/choice/{id}")
    Call<WorkBookTestResponse> getTestApi(@Path("id") Long id);

    @GET("api/v1/choices")
    Call<WorkBookResponse> getChoiceList();

    @POST("api/v1/sign-in")
    Call<SginInResponse> SignIn(@Body Map<String,Object> Map);

    @POST("api/v1/sign-up")
    Call<SginUpResponse> SignUp(@Body Map<String, Object>  Map);

    @GET("api/v1/member")
    Call<SginInResponse> GetUser(@Header("Authorization") String token);


}
