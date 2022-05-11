package com.smp.frontend.workbook;

import com.smp.frontend.BuildConfig;
import com.smp.frontend.member.MemberController;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClientWorkbook {

    private static RetrofitClientWorkbook instance = null;
    private  static WorkbookController WorkbookController;
    private static String baseUrl = BuildConfig.BASEURL;

    private RetrofitClientWorkbook() {
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

        WorkbookController = retrofit.create(WorkbookController.class);

    }
    public static  RetrofitClientWorkbook getInstance(){
        if(instance == null){
            instance = new RetrofitClientWorkbook();
        }
        return instance;
    }
    public  static  WorkbookController getRetrofitInterface(){
        return WorkbookController;
    }
}
