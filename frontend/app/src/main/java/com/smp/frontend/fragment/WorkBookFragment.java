package com.smp.frontend.fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.smp.frontend.R;
import com.smp.frontend.restAPi.RestApi;
import com.smp.frontend.restAPi.singletonGson;
import com.smp.frontend.restAPi.TestApi;
import com.smp.frontend.restAPi.TestDataApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class WorkBookFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private TextView test1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://d272-121-185-119-51.jp.ngrok.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RestApi testApi = retrofit.create(RestApi.class);
        Call<TestDataApi> test = testApi.getChoiceList();

        test.enqueue(new Callback<TestDataApi>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<TestDataApi> call, Response<TestDataApi> response) {
                singletonGson instance = singletonGson.getInstance();

                TestDataApi body = response.body();
                System.out.println("body.getCount() = " + body.getCount());
                System.out.println("body.getData() = " + body.getData());
                List<?> data = body.getData();

                Gson gson = new Gson();

                for (int i = 0; i < body.getCount(); i++) {
                    TestApi parsing = (TestApi) instance.parsing(
                            instance.toJson(data.get(i)),
                            TestApi.class);
                    System.out.println("parsing.getContent() = " + parsing.getContent());
                }
            }

            @Override
            public void onFailure(Call<TestDataApi> call, Throwable t) {

            }
        });

        return inflater.inflate(R.layout.fragment_work_book, container, false);

    }
}