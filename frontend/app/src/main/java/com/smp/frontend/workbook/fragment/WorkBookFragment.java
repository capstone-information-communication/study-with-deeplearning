package com.smp.frontend.workbook.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.smp.frontend.common.PreferencesManager;
import com.smp.frontend.R;
import com.smp.frontend.common.gsonParsing;
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
    private RecyclerView recyclerView;
    private int page =0, lastVisibleItemPosition;
    private boolean pageDone = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private TextView test1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_work_book, container, false);
        recyclerView =(RecyclerView) view.findViewById(R.id.rv_workBook);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItemPosition = ((LinearLayoutManager)recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                int itemTotalCount = recyclerView.getAdapter().getItemCount() - 1;

                if(lastVisibleItemPosition == itemTotalCount && pageDone == false){
                    System.out.println("list.size() = " + list.size());
                    page++;
                    getList(page);

                }

            }
        });

        return view;

    }

    @Override
    public void onStop() {
        super.onStop();
        list = new ArrayList<>();
        page= 0;
        pageDone = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        getList(page);
    }
    public void getList(int page){
        RetrofitClientWorkbook retrofitClient = RetrofitClientWorkbook.getInstance();
        WorkbookController workbookController = RetrofitClientWorkbook.getRetrofitInterface();
        Call<WorkBookResponseDto> test = workbookController.getWorkbook(PreferencesManager.getString(getContext(),"token"),page);

        test.enqueue(new Callback<WorkBookResponseDto>() {
            @Override
            public void onResponse(Call<WorkBookResponseDto> call, Response<WorkBookResponseDto> response) {
                gsonParsing instance = gsonParsing.getInstance();
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

                        list.add(new WorkBookItemData(id, title, description,page));
                        adapter = new WorkBookAdapter(getActivity(), list);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerView.setAdapter(adapter);
                        recyclerView.scrollToPosition(page*10 -10);
                        recyclerView.setHasFixedSize(true);
                    }
                }
                else {
                    System.out.println("페이지 끝");
                    pageDone = true;
                }
            }


            @Override
            public void onFailure(Call<WorkBookResponseDto> call, Throwable t) {

            }
        });
    }
}