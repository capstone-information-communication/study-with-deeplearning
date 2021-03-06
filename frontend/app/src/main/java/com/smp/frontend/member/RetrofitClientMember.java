package com.smp.frontend.member;

import com.smp.frontend.BuildConfig;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClientMember {

    private static RetrofitClientMember instance = null;
    private static MemberController MemberController;
    //사용하고 있는 서버 BASE 주소
    private static String baseUrl = BuildConfig.BASEURL;

    private RetrofitClientMember() {
        //로그를 보기 위한 Interceptor
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        //retrofit 설정
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client) //로그 기능 추가
                .build();

        MemberController = retrofit.create(MemberController.class);
    }

    public static RetrofitClientMember getInstance() {
        if (instance == null) {
            instance = new RetrofitClientMember();
        }
        return instance;
    }

    public static MemberController getRetrofitInterface() {
        return MemberController;
    }

}
