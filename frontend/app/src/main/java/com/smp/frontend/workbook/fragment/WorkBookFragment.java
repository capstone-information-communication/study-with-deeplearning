package com.smp.frontend.workbook.fragment;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mancj.materialsearchbar.MaterialSearchBar;
import com.smp.frontend.global.PreferencesManager;
import com.smp.frontend.R;
import com.smp.frontend.global.gsonParsing;
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

import static android.content.Context.INPUT_METHOD_SERVICE;


public class WorkBookFragment extends Fragment implements MaterialSearchBar.OnSearchActionListener {
    private ArrayList<WorkBookItemData> list = new ArrayList<>();
    private View view;
    private WorkBookAdapter adapter;
    private RecyclerView recyclerView;
    private int page =0, lastVisibleItemPosition;
    private String title,description;
    private boolean pageDone = false,search=true;
    private List<String> lastSearches;
    private MaterialSearchBar searchBar;
    private Spinner spinner;
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
                    if(search == true){
                        System.out.println("list.size() = " + list.size());
                        page++;
                        getSearchList(page,title,description);
                    }
                    else{
                        System.out.println("list.size() = " + list.size());
                        page++;
                        getList(page);
                    }

                }

            }
        });
        searchBar = (MaterialSearchBar) view.findViewById(R.id.searchBar);
        searchBar.setOnSearchActionListener(this);
        lastSearches =searchBar.getLastSuggestions();
        spinner = view.findViewById(R.id.spinner);
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
        search= false;
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

                        list.add(new WorkBookItemData(id, title, description,page,search));
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
    public void getSearchList(int page,String title, String description){
        search = true;
        RetrofitClientWorkbook retrofitClient = RetrofitClientWorkbook.getInstance();
        WorkbookController workbookController = RetrofitClientWorkbook.getRetrofitInterface();
        Call<WorkBookResponseDto> test = workbookController.getWorkBookSearch(PreferencesManager.getString(getContext(),"token"),title,description,page);

        test.enqueue(new Callback<WorkBookResponseDto>() {
            @Override
            public void onResponse(Call<WorkBookResponseDto> call, Response<WorkBookResponseDto> response) {
                if(response.code() == 200) {
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

                            list.add(new WorkBookItemData(id, title, description, page, search));
                            adapter = new WorkBookAdapter(getActivity(), list);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                            recyclerView.setAdapter(adapter);
                            recyclerView.scrollToPosition(page * 10 - 10);
                            recyclerView.setHasFixedSize(true);
                        }
                    } else if (body.getCount() == 0 && page == 0) {
                        list = new ArrayList<>();
                        adapter = new WorkBookAdapter(getActivity(), list);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerView.setAdapter(adapter);
                        recyclerView.scrollToPosition(page * 10 - 10);
                        recyclerView.setHasFixedSize(true);
                    } else {
                        System.out.println("페이지 끝");
                        pageDone = true;
                    }
                }
                else if(response.code() == 400){
                    System.out.println("response.errorBody().toString() = " + response.errorBody().toString());
                }
            }


            @Override
            public void onFailure(Call<WorkBookResponseDto> call, Throwable t) {

            }
        });
    }
    @Override
    public void onSearchStateChanged(boolean enabled) {
        System.out.println("enabled = " + enabled);
    }

    @Override
    public void onSearchConfirmed(CharSequence text) {
        int selectedItemPosition = spinner.getSelectedItemPosition();
        page=0;
        list = new ArrayList<>();
        int keyevent =66;
        if( keyevent == KeyEvent.KEYCODE_ENTER){
            System.out.println("엔터키 입력");
            InputMethodManager manager = (InputMethodManager)getActivity().getSystemService(INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        switch (selectedItemPosition){
            case 0:
                title =text.toString();
                description="";
                System.out.println("title = " + title + description);
                getSearchList(page,title,description);
                break;
            case 1:
                title ="";
                description=text.toString();
                System.out.println("description = " + title + description);
                getSearchList(page,title,description);
                break;
            case 2:
                title =text.toString();
                description=text.toString();
                System.out.println("all = " + title + description);
                getSearchList(page,title,description);
                break;
        }
    }

    @Override
    public void onButtonClicked(int buttonCode) {
        System.out.println("buttonCode = " + buttonCode);

    }
}