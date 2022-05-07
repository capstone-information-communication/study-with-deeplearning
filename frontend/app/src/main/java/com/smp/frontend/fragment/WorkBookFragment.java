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
import com.smp.frontend.BuildConfig;
import com.smp.frontend.R;
import com.smp.frontend.restAPi.RestApi;
import com.smp.frontend.restAPi.RetrofitClient;
import com.smp.frontend.restAPi.SingletonGson;
import com.smp.frontend.restAPi.WorkBookTestResponse;
import com.smp.frontend.restAPi.WorkBookResponse;

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

        RetrofitClient retrofitClient = RetrofitClient.getInstance();
        RestApi restApi = RetrofitClient.getRetrofitInterface();
        Call<WorkBookResponse> test = restApi.getChoiceList();

        test.enqueue(new Callback<WorkBookResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<WorkBookResponse> call, Response<WorkBookResponse> response) {
                SingletonGson instance = SingletonGson.getInstance();

                WorkBookResponse body = response.body();
                System.out.println("body.getCount() = " + body.getCount());
                System.out.println("body.getData() = " + body.getData());
                List<?> data = body.getData();

                Gson gson = new Gson();

                for (int i = 0; i < body.getCount(); i++) {
                    WorkBookTestResponse parsing = (WorkBookTestResponse) instance.parsing(
                            instance.toJson(data.get(i)),
                            WorkBookTestResponse.class);
                    System.out.println("parsing.getContent() = " + parsing.getContent());
                }
            }

            @Override
            public void onFailure(Call<WorkBookResponse> call, Throwable t) {

            }
        });

        return inflater.inflate(R.layout.fragment_work_book, container, false);

    }
}