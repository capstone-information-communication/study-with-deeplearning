package com.smp.frontend.workbook.fragment;

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
import com.smp.frontend.restAPi.gsonParsing;
import com.smp.frontend.workbook.RetrofitClientWorkbook;
import com.smp.frontend.workbook.WorkbookController;
import com.smp.frontend.workbook.dto.WorkBookTestResponse;
import com.smp.frontend.workbook.dto.WorkBookResponseDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WorkBookFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private TextView test1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        RetrofitClientWorkbook retrofitClient = RetrofitClientWorkbook.getInstance();
        WorkbookController workbookController = RetrofitClientWorkbook.getRetrofitInterface();
        Call<WorkBookResponseDto> test = workbookController.getChoiceList();

        test.enqueue(new Callback<WorkBookResponseDto>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<WorkBookResponseDto> call, Response<WorkBookResponseDto> response) {
                gsonParsing instance = gsonParsing.getInstance();

                WorkBookResponseDto body = response.body();
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
            public void onFailure(Call<WorkBookResponseDto> call, Throwable t) {

            }
        });

        return inflater.inflate(R.layout.fragment_work_book, container, false);

    }
}