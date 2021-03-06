package com.smp.frontend.likeWorkbook.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
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
    private int page =0, lastVisibleItemPosition;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public static likeWorkBookFragment newInstance(int number) {
        likeWorkBookFragment likeWorkBookFragment = new likeWorkBookFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("number", number);
        likeWorkBookFragment.setArguments(bundle);
        return likeWorkBookFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_likebook, container, false);
        recyclerView =(RecyclerView) view.findViewById(R.id.rv_likebook);
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
                    getlikeList(page);
                    }
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onResume() {
        list = new ArrayList<>();
            getlikeList(page);
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
                        System.out.println("????????? ???");
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