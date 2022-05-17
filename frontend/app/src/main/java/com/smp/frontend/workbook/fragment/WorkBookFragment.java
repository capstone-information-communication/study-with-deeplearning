package com.smp.frontend.workbook.fragment;

import android.os.Build;
import android.os.Bundle;
import android.os.PerformanceHintManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.smp.frontend.PreferencesManager;
import com.smp.frontend.R;
import com.smp.frontend.restAPi.gsonParsing;
import com.smp.frontend.workbook.RetrofitClientWorkbook;
import com.smp.frontend.workbook.WorkbookController;
import com.smp.frontend.workbook.dto.WorkBookResponseDto;
import com.smp.frontend.workbook.dto.WorkBookTestResponse;
import com.smp.frontend.workbook.list.WorkBookAdapter;
import com.smp.frontend.workbook.list.WorkBookItemData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WorkBookFragment extends Fragment {
    private ArrayList<WorkBookItemData> list = new ArrayList<>();
    private View view;
    private WorkBookAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private TextView test1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_work_book, container, false);
        RecyclerView recyclerView =(RecyclerView) view.findViewById(R.id.rv_workBook);
        RetrofitClientWorkbook retrofitClient = RetrofitClientWorkbook.getInstance();
        WorkbookController workbookController = RetrofitClientWorkbook.getRetrofitInterface();
        Call<WorkBookResponseDto> test = workbookController.getWorkbook(PreferencesManager.getString(getContext(),"token"));

        test.enqueue(new Callback<WorkBookResponseDto>() {
            @Override
            public void onResponse(Call<WorkBookResponseDto> call, Response<WorkBookResponseDto> response) {
                gsonParsing instance = gsonParsing.getInstance();
                WorkBookResponseDto body = response.body();
                List<?> data = body.getData();

                for (int i = 0; i < body.getCount(); i++) {
                    WorkBookTestResponse parsing = (WorkBookTestResponse) instance.parsing(
                            instance.toJson(data.get(i)),
                            WorkBookTestResponse.class);
                    int id = (int)parsing.getId();
                    String title = parsing.getTitle();
                    String description = parsing.getDescription();

                    list.add(new WorkBookItemData(id,title,description));
                    recyclerView.setHasFixedSize(true);
                    adapter = new WorkBookAdapter(getActivity(), list);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.setAdapter(adapter);
                    recyclerView.setHasFixedSize(true);
                }
            }

            @Override
            public void onFailure(Call<WorkBookResponseDto> call, Throwable t) {

            }
        });

        return view;

    }

    @Override
    public void onStop() {
        super.onStop();
        list = new ArrayList<>();
    }
}