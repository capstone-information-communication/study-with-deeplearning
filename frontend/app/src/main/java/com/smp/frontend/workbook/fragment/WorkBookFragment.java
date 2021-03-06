package com.smp.frontend.workbook.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mancj.materialsearchbar.MaterialSearchBar;
import com.smp.frontend.global.PreferencesManager;
import com.smp.frontend.R;
import com.smp.frontend.global.gsonParsing;
import com.smp.frontend.likeWorkbook.fragment.likeWorkBookFragment;
import com.smp.frontend.member.activity.LoginActivity;
import com.smp.frontend.member.activity.MainActivity;
import com.smp.frontend.workbook.RetrofitClientWorkbook;
import com.smp.frontend.workbook.WorkbookController;
import com.smp.frontend.workbook.dto.WorkBookResponseDto;
import com.smp.frontend.workbook.dto.WorkBookTestResponse;
import com.smp.frontend.workbook.list.WorkBookAdapter;
import com.smp.frontend.workbook.list.WorkBookItemData;
import com.smp.frontend.workbook.list.RecyclerItemTouchHelper;
import com.smp.frontend.workbook.list.WorkBookQuestionItemData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.INPUT_METHOD_SERVICE;


public class WorkBookFragment extends Fragment implements MaterialSearchBar.OnSearchActionListener, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
    private ArrayList<WorkBookItemData> list = new ArrayList<>();
    private List<WorkBookItemData> myArrayData = new ArrayList();
    private View view;
    private WorkBookAdapter adapter;
    private RecyclerView recyclerView;
    private int page =0, lastVisibleItemPosition;
    private String title,description;
    private boolean pageDone = false,search=true;
    //search
    private List<String> lastSearches;
    private MaterialSearchBar searchBar;
    private Spinner spinner;
    //retrofit
    private RetrofitClientWorkbook retrofitClient = RetrofitClientWorkbook.getInstance();
    private WorkbookController workbookController = RetrofitClientWorkbook.getRetrofitInterface();
    private Drawable drawable;
    private Map<Long,Drawable> likeMap= new HashMap<>();
    private String sort="????????????", back ="????????????";
    private Button sort_btn;
    private boolean chk = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public static WorkBookFragment newInstance(int number) {
        WorkBookFragment WorkBookFragment = new WorkBookFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("number", number);
        WorkBookFragment.setArguments(bundle);
        return WorkBookFragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_work_book, container, false);
        drawable = getContext().getDrawable(R.drawable.ic_baseline_favorite_border_24);
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
                        page++;
                        getSearchList(page,title,description);
                    }
                    else{
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

        sort_btn = view.findViewById(R.id.btn_sort);
        sort_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //???????????? sort??? ArrayList
                if(chk == false) {
                    myArrayData = new ArrayList<>();
                    for (int i = 0; i < adapter.getItemCount(); i++) {
                        myArrayData.add(adapter.getList(i));
                    }
                    for (int j = 0; j < myArrayData.size(); j++) {
                        for (int k = 0; k < myArrayData.size() - j - 1; k++) {
                            if (myArrayData.get(k).getLikeCount() < myArrayData.get(k + 1).getLikeCount()) {
                                WorkBookItemData tmp = myArrayData.get(k);
                                myArrayData.set(k, myArrayData.get(k + 1));
                                myArrayData.set(k + 1, tmp);
                            }
                        }
                    }

                    adapter = new WorkBookAdapter(getActivity(), myArrayData);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.setAdapter(adapter);
                    chk = true;
                    sort_btn.setText(back);
                }
                else if(chk == true){
                    myArrayData = new ArrayList<>();
                    list = new ArrayList<>();
                    page=0;
                    pageDone = false;
                    checkLike(page);
                    getList(page);
                    chk =false;
                    sort_btn.setText(sort);
                }

            }
        });


        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);


        return view;

    }
    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        myArrayData = new ArrayList<>();
        list = new ArrayList<>();
        page=0;
        pageDone = false;
        chk =false;
        sort_btn.setText(sort);
        checkLike(page);
        getList(page);
    }
    public void getList(int page){
        search= false;
        checkLike(page);
        Call<WorkBookResponseDto> test = workbookController.getWorkbook(PreferencesManager.getString(getContext(),"token"),page);
        test.enqueue(new Callback<WorkBookResponseDto>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(Call<WorkBookResponseDto> call, Response<WorkBookResponseDto> response) {
                gsonParsing instance = gsonParsing.getInstance();

                if(response.code() == 200){
                    WorkBookResponseDto body = response.body();
                    List<?> data = body.getData();
                    if (body.getCount() != 0) {
                        for (int i = 0; i < body.getCount(); i++) {
                            WorkBookTestResponse parsing = (WorkBookTestResponse) instance.parsing(
                                    instance.toJson(data.get(i)),
                                    WorkBookTestResponse.class);
                            int id = (int) parsing.getId();

                            Set<Long> keySet1 = likeMap.keySet();
                            for (Long key : keySet1) {
                                if(key == id){
                                    System.out.println("key = " + key);
                                    drawable = getContext().getDrawable(R.drawable.ic_baseline_favorite_24);
                                    break;
                                }
                                else{
                                    drawable = getContext().getDrawable(R.drawable.ic_baseline_favorite_border_24);
                                }
                            }

                            String title = parsing.getTitle();
                            String description = parsing.getDescription();
                            int likeCount = (int)parsing.getLikeCount();
                            list.add(new WorkBookItemData(id, title, description,likeCount,page,search,drawable));
                            adapter = new WorkBookAdapter(getActivity(), list);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                            recyclerView.setAdapter(adapter);
                            recyclerView.scrollToPosition(page*10 -10);
                            recyclerView.setHasFixedSize(true);
                        }
                    }
                    else {
                        System.out.println("????????? ???");
                        pageDone = true;
                    }
                }
                else if(response.code() == 400){
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        String message = jsonObject.get("message").toString();
                        System.out.println("message = " + message);
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();

                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                        System.out.println("????????? ?????? ????????? message??? ?????? ??? ??????");
                    }
                }

            }


            @Override
            public void onFailure(Call<WorkBookResponseDto> call, Throwable t) {

            }
        });
    }
    public void getSearchList(int page,String title, String description){
        search = true;
        checkLike(page);
        Call<WorkBookResponseDto> test = workbookController.getWorkBookSearch(PreferencesManager.getString(getContext(),"token"),title,description,page);
        test.enqueue(new Callback<WorkBookResponseDto>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(Call<WorkBookResponseDto> call, Response<WorkBookResponseDto> response) {
                if(response.code() == 200) {
                    sort_btn.setText(sort);
                    chk = false;
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
                            int likeCount = parsing.getLikeCount();

                            Set<Long> keySet1 = likeMap.keySet();
                            for (Long key : keySet1) {
                                if(key == id){
                                    System.out.println("key = " + key);
                                    drawable = getContext().getDrawable(R.drawable.ic_baseline_favorite_24);

                                    break;
                                }
                                else{
                                    drawable = getContext().getDrawable(R.drawable.ic_baseline_favorite_border_24);
                                }
                            }
                            list.add(new WorkBookItemData(id, title, description,likeCount, page, search,drawable));
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
                        System.out.println("????????? ???");
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
            System.out.println("????????? ??????");
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

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof WorkBookAdapter.Holder) {
            // get the removed item name to display it in snack bar
            String name = list.get(viewHolder.getAbsoluteAdapterPosition()).getTitle();
            long WorkBookId = list.get(viewHolder.getAbsoluteAdapterPosition()).getId();

            // backup of removed item for undo purpose
             WorkBookItemData deletedItem = list.get(viewHolder.getAbsoluteAdapterPosition());
             int deletedIndex = viewHolder.getAbsoluteAdapterPosition();

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    getContext());
            alertDialogBuilder.setTitle(name);
            alertDialogBuilder
                    .setMessage("????????? ??? ???????????? ????????????????")
                    .setCancelable(false)
                    .setPositiveButton("???",
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialog, int id) {
                                     RetrofitClientWorkbook retrofitClient = RetrofitClientWorkbook.getInstance();
                                     WorkbookController workbookController = RetrofitClientWorkbook.getRetrofitInterface();
                                    Call<WorkBookResponseDto> responseCall = workbookController.DeleteWorkBook(PreferencesManager.getString(getContext(),"token"),WorkBookId);
                                    responseCall.enqueue(new Callback<WorkBookResponseDto>() {
                                        @Override
                                        public void onResponse(Call<WorkBookResponseDto> call, Response<WorkBookResponseDto> response) {
                                            if(response.code() == 200){
                                                adapter.removeItem(viewHolder.getAbsoluteAdapterPosition());
                                            }
                                            else if(response.code() == 400){
                                                try {
                                                    JSONObject jsonObject = new JSONObject(response.errorBody().string());
                                                    String message = jsonObject.get("message").toString();
                                                    Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();
                                                    adapter.notifyDataSetChanged();
                                                } catch (IOException | JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            else  if(response.code() == 500){
                                                Toast.makeText(getContext(),"???????????? ????????????.",Toast.LENGTH_LONG).show();
                                                adapter.notifyDataSetChanged();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<WorkBookResponseDto> call, Throwable t) {

                                        }
                                    });

                                }
                            })
                    .setNegativeButton("??????",
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialog, int id) {
                                    adapter.notifyDataSetChanged();
                                }
                            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }
    public void checkLike(int page) {
        Call<WorkBookResponseDto> likebookcheck = workbookController.getlikeWorkBook(PreferencesManager.getString(getContext(), "token"), page);
        likebookcheck.enqueue(new Callback<WorkBookResponseDto>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(Call<WorkBookResponseDto> call, Response<WorkBookResponseDto> response) {
                gsonParsing instance = gsonParsing.getInstance();
                WorkBookResponseDto body = response.body();
                if (response.code() == 200) {
                    List<?> data = body.getData();
                    NONE:
                    for (int i = 0; i < body.getCount(); i++) {
                        WorkBookTestResponse parsing = (WorkBookTestResponse) instance.parsing(
                                instance.toJson(data.get(i)),
                                WorkBookTestResponse.class);
                        long id = parsing.getId();
                        System.out.println("123123123 = " + id);
                        drawable = getContext().getDrawable(R.drawable.ic_baseline_favorite_24);
                        likeMap.put(id, drawable);

                        if (response.body().getCount() == 0) {
                            break NONE;
                        }

                    }

                }
            }

            @Override
            public void onFailure(Call<WorkBookResponseDto> call, Throwable t) {

            }
        });
    }
}