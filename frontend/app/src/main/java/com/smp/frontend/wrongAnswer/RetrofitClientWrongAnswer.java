package com.smp.frontend.wrongAnswer;

import com.smp.frontend.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClientWrongAnswer {
    private static RetrofitClientWrongAnswer instance = null;
    private static WrongAnswerController WrongAnswerController;
    private static String baseUrl = BuildConfig.BASEURL;


    private RetrofitClientWrongAnswer(){
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

        WrongAnswerController = retrofit.create(WrongAnswerController.class);
    }
    public static RetrofitClientWrongAnswer getInstance(){
        if(instance == null){
            instance = new RetrofitClientWrongAnswer();
        }
        return instance;
    }
    public static WrongAnswerController getRetrofitInterface(){
        return WrongAnswerController;
    }

}
