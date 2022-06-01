package com.smp.frontend.likeWorkbook.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.smp.frontend.R;
import com.smp.frontend.global.PreferencesManager;
import com.smp.frontend.global.gsonParsing;
import com.smp.frontend.likeWorkbook.list.likeBookAdapter;
import com.smp.frontend.likeWorkbook.list.likeBookItemData;
import com.smp.frontend.member.activity.LoginActivity;
import com.smp.frontend.member.activity.MainActivity;
import com.smp.frontend.workbook.RetrofitClientWorkbook;
import com.smp.frontend.workbook.WorkbookController;
import com.smp.frontend.workbook.dto.WorkBookResponseDto;
import com.smp.frontend.workbook.dto.WorkBookTestResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class likeWorkBookFragment extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private RetrofitClientWorkbook retrofitClient = RetrofitClientWorkbook.getInstance();
    private WorkbookController workbookController = RetrofitClientWorkbook.getRetrofitInterface();
    private List<likeBookItemData> list = new ArrayList<>();
    private likeBookAdapter adapter;
    private boolean pageDone =false;
    private int page =0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_likebook, container, false);
        Button logout;
        logout = view.findViewById(R.id.btn_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreferencesManager.removeValue(getActivity().getApplicationContext());
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        getlikeList(page);
        recyclerView =(RecyclerView) view.findViewById(R.id.rv_likebook);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onResume() {
        list = new ArrayList<>();
        super.onResume();
    }
    public void getlikeList(int page){
        Call<WorkBookResponseDto> likebook = workbookController.getlikeWorkBook(PreferencesManager.getString(getContext(),"token"),page);
        likebook.enqueue(new Callback<WorkBookResponseDto>() {
            @Override
            public void onResponse(Call<WorkBookResponseDto> call, Response<WorkBookResponseDto> response) {
                gsonParsing instance = gsonParsing.getInstance();
                if (response.code() == 400) {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                } else if (response.code() == 200) {
                    WorkBookResponseDto body = response.body();
                    List<?> data = body.getData();
                    if (body.getCount() != 0) {
                        for (int i = 0; i < body.getCount(); i++) {
                            WorkBookTestResponse parsing = (WorkBookTestResponse) instance.parsing(
                                    instance.toJson(data.get(i)),
                                    WorkBookTestResponse.class);
                            int id = (int) parsing.getId();
                            String title = parsing.getTitle();
                            String description = parsing.getDescription();
                            int likeCount = (int) parsing.getLikeCount();
                            list.add(new likeBookItemData(id, title, description));
                            adapter = new likeBookAdapter(getActivity(), list);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                            recyclerView.setAdapter(adapter);
                            recyclerView.scrollToPosition(page * 10 - 10);
                            recyclerView.setHasFixedSize(true);
                        }
                    } else {
                        System.out.println("페이지 끝");
                        pageDone = true;
                    }
                }
            }

            @Override
            public void onFailure(Call<WorkBookResponseDto> call, Throwable t) {

            }
        });
    }
}