package com.smp.frontend.member;

import com.smp.frontend.member.dto.MemberSignInRequestDto;
import com.smp.frontend.member.dto.MemberSignUpRequestDto;
import com.smp.frontend.member.dto.MemberSginInResponseDto;
import com.smp.frontend.member.dto.MemberSginUpResponseDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface MemberController {
    @POST("api/v1/sign-in")
    Call<MemberSginInResponseDto> SignIn(@Body MemberSignInRequestDto request);

    @POST("api/v1/sign-up")
    Call<MemberSginUpResponseDto> SignUp(@Body MemberSignUpRequestDto request);

    @GET("api/v1/member")
    Call<MemberSginInResponseDto> GetUser(@Header("Authorization") String token);
}
