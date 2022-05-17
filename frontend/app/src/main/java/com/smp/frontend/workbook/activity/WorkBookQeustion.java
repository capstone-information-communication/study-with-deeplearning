package com.smp.frontend.workbook.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.smp.frontend.PreferencesManager;
import com.smp.frontend.R;
import com.smp.frontend.common.gsonParsing;
import com.smp.frontend.workbook.RetrofitClientWorkbook;
import com.smp.frontend.workbook.WorkbookController;
import com.smp.frontend.workbook.dto.WorkBookResponseDto;
import com.smp.frontend.workbook.dto.WorkBookTestResponse;
import com.smp.frontend.workbook.list.WorkBookQuestionAdapter;
import com.smp.frontend.workbook.list.WorkBookQuestionItemData;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkBookQeustion extends AppCompatActivity {
    private int ID;
    private RecyclerView recyclerView;
    private List<WorkBookQuestionItemData> list= new ArrayList<>();;
    private WorkBookQuestionAdapter adapter;
    private List<String> choiceList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_book_qeustion);

        Intent intent = getIntent();
        ID = intent.getIntExtra("ID",0);
        recyclerView = findViewById(R.id.rv_WrongAnswers);

        RetrofitClientWorkbook retrofitClientWorkbook = RetrofitClientWorkbook.getInstance();
        WorkbookController workbookController = RetrofitClientWorkbook.getRetrofitInterface();
        Call<WorkBookResponseDto> responseCall = workbookController.getWorkbook(PreferencesManager.getString(getApplicationContext(),"token"));
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
                        long id =  parsing.getId();
                        if (id == ID) {
                            for (int j = 0; j < parsing.getQuestionList().size(); j++) {
                                System.out.println("parsing.getQuestionList() = " + parsing.getQuestionList());
                                try {
                                    //questionList
                                    WorkBookTestResponse parsing2 = (WorkBookTestResponse) instance.parsing(
                                            instance.ArrToString(instance.toJsonArr(parsing.getQuestionList()), j),
                                            WorkBookTestResponse.class
                                    );
                                    //chocieList
                                    long qid = parsing2.getId();
                                    String qtitle = parsing2.getTitle();
                                    String qcontent = parsing2.getContent();
                                    System.out.println("parsing2.getQuestionList() = " + parsing2.getChoiceList());
                                    for (int k = 0; k < parsing2.getChoiceList().size(); k++) {

                                        WorkBookTestResponse parsing3 = (WorkBookTestResponse) instance.parsing(
                                                instance.ArrToString(instance.toJsonArr(parsing2.getChoiceList()), k),
                                                WorkBookTestResponse.class
                                        );
                                        String Ccontent = parsing3.getContent();
                                        choiceList.add(Ccontent);
                                    }
                                    list.add(new WorkBookQuestionItemData(qid, qtitle, qcontent, choiceList));
                                    recyclerView.setHasFixedSize(true);
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