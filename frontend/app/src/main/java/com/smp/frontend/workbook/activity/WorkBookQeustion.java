package com.smp.frontend.workbook.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.smp.frontend.global.PreferencesManager;
import com.smp.frontend.R;
import com.smp.frontend.global.WorkBookQuestionListDto;
import com.smp.frontend.global.choiceListDto;
import com.smp.frontend.global.gsonParsing;
import com.smp.frontend.member.activity.MainActivity;
import com.smp.frontend.workbook.RetrofitClientWorkbook;
import com.smp.frontend.workbook.WorkbookController;
import com.smp.frontend.workbook.dto.WorkBookCheckRequestDto;
import com.smp.frontend.workbook.dto.WorkBookResponseDto;
import com.smp.frontend.workbook.dto.WorkBookTestResponse;
import com.smp.frontend.workbook.list.WorkBookQuestionAdapter;
import com.smp.frontend.workbook.list.WorkBookQuestionItemData;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkBookQeustion extends AppCompatActivity {
    private int ID,page;
    private String title,description;
    private boolean search;
    private RecyclerView recyclerView;
    private List<WorkBookQuestionItemData> list= new ArrayList<>();;
    private WorkBookQuestionAdapter adapter;
    private Button check_btn;
    private Toast toast;
    private RetrofitClientWorkbook retrofitClientWorkbook = RetrofitClientWorkbook.getInstance();
    private WorkbookController workbookController = RetrofitClientWorkbook.getRetrofitInterface();
    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_book_qeustion);

        Intent intent = getIntent();
        ID = intent.getIntExtra("ID",0);
        page = intent.getIntExtra("page",0);
        title = intent.getStringExtra("title");
        description = intent.getStringExtra("descritpion");
        search = intent.getBooleanExtra("search",true);
        recyclerView = findViewById(R.id.rv_WrongAnswers);

        getQeustion();

        check_btn = findViewById(R.id.check_btn);
        check_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Long> questionList = new ArrayList<>();
                if(adapter.getChoiceCount() <= 0 || adapter.getChoiceCount() != adapter.getQeustionCount()){
                    System.out.println("adapter = " + adapter.getQeustionCount());
                    System.out.println("adapter = " + adapter.getChoiceCount());
                    System.out.println("문제 선택 해주세요 하나라도");
                    return;
                }
                Set<Integer> keySet = adapter.getQuestion().keySet();
                for (Integer key : keySet) {
                    System.out.println(key + " : " + adapter.getQuestion().get(key));
                    questionList.add(adapter.getQuestion().get(key));
                }

                WorkBookCheckRequestDto check = new WorkBookCheckRequestDto(ID,questionList);
                Call<WorkBookTestResponse> request = workbookController.WorkBookCheck(PreferencesManager.getString(getApplicationContext(),"token")
                        ,check
                );
                request.enqueue(new Callback<WorkBookTestResponse>() {
                    @Override
                    public void onResponse(Call<WorkBookTestResponse> call, Response<WorkBookTestResponse> response) {
                        if(response.code() == 201){
                            WorkBookTestResponse data =  response.body();
                            System.out.println("response = " + response.body());
                            toast.makeText(getApplicationContext(),"채점 완료",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        }

                        else{
                            try {
                                System.out.println("response.errorBody().string() = " + response.errorBody().string());
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<WorkBookTestResponse> call, Throwable t) {

                    }
                });
            }
        });

    }

    public void getQeustion() {
        if (search == false) {
            list = new ArrayList<>();
            Call<WorkBookResponseDto> responseCall = workbookController.getWorkbook(PreferencesManager.getString(getApplicationContext(), "token"), page);
            responseCall.enqueue(new Callback<WorkBookResponseDto>() {
                @Override
                public void onResponse(Call<WorkBookResponseDto> call, Response<WorkBookResponseDto> response) {
                    gsonParsing instance = gsonParsing.getInstance();
                    WorkBookResponseDto body = response.body();
                    if (response.code() == 200) {
                        List<?> data = body.getData();

                        for (int i = 0; i < body.getCount(); i++) {
                            WorkBookTestResponse parsing = (WorkBookTestResponse) instance.parsing(
                                    instance.toJson(data.get(i)),
                                    WorkBookTestResponse.class);
                            long id = parsing.getId();
                            if (id == ID) {
                                for (int j = 0; j < parsing.getQuestionList().size(); j++) {
                                    try {
                                        //questionList
                                        WorkBookTestResponse parsing2 = (WorkBookTestResponse) instance.parsing(
                                                instance.ArrToString(instance.toJsonArr(parsing.getQuestionList()), j),
                                                WorkBookTestResponse.class
                                        );
                                        long qid = parsing2.getId();
                                        String qtitle = parsing2.getTitle();
                                        String qcontent = parsing2.getContent();
                                        String category = parsing2.getCategory();
                                        WorkBookQuestionListDto WorkBookQuestionListDto = new WorkBookQuestionListDto(qid, qtitle, qcontent, category);
                                        //chocieList
                                        List<?> choiceList = parsing2.getChoiceList();

                                        list.add(new WorkBookQuestionItemData(id, WorkBookQuestionListDto, choiceList));
                                        adapter = new WorkBookQuestionAdapter(getApplicationContext(), list);
                                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                        recyclerView.setAdapter(adapter);
                                        recyclerView.setHasFixedSize(true);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<WorkBookResponseDto> call, Throwable t) {

                }
            });
        }
        else{
            list = new ArrayList<>();
            Call<WorkBookResponseDto> responseCall = workbookController.getWorkBookSearch(PreferencesManager.getString(getApplicationContext(), "token"),title,description, page);
            responseCall.enqueue(new Callback<WorkBookResponseDto>() {
                @Override
                public void onResponse(Call<WorkBookResponseDto> call, Response<WorkBookResponseDto> response) {
                    gsonParsing instance = gsonParsing.getInstance();
                    WorkBookResponseDto body = response.body();
                    if (response.code() == 200) {
                        List<?> data = body.getData();

                        for (int i = 0; i < body.getCount(); i++) {
                            WorkBookTestResponse parsing = (WorkBookTestResponse) instance.parsing(
                                    instance.toJson(data.get(i)),
                                    WorkBookTestResponse.class);
                            long id = parsing.getId();
                            if (id == ID) {
                                for (int j = 0; j < parsing.getQuestionList().size(); j++) {
                                    try {
                                        //questionList
                                        WorkBookTestResponse parsing2 = (WorkBookTestResponse) instance.parsing(
                                                instance.ArrToString(instance.toJsonArr(parsing.getQuestionList()), j),
                                                WorkBookTestResponse.class
                                        );
                                        long qid = parsing2.getId();
                                        String qtitle = parsing2.getTitle();
                                        String qcontent = parsing2.getContent();
                                        String category = parsing2.getCategory();
                                        WorkBookQuestionListDto WorkBookQuestionListDto = new WorkBookQuestionListDto(qid, qtitle, qcontent, category);
                                        //chocieList
                                        List<?> choiceList = parsing2.getChoiceList();

                                        list.add(new WorkBookQuestionItemData(id, WorkBookQuestionListDto, choiceList));
                                        adapter = new WorkBookQuestionAdapter(getApplicationContext(), list);
                                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                        recyclerView.setAdapter(adapter);
                                        recyclerView.setHasFixedSize(true);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
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
}