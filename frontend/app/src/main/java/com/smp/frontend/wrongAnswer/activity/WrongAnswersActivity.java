package com.smp.frontend.wrongAnswer.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.smp.frontend.PreferencesManager;
import com.smp.frontend.R;
import com.smp.frontend.restAPi.gsonParsing;
import com.smp.frontend.wrongAnswer.RetrofitClientWrongAnswer;
import com.smp.frontend.wrongAnswer.WrongAnswerController;
import com.smp.frontend.wrongAnswer.dto.WrongAnswerResponseDto;
import com.smp.frontend.wrongAnswer.list.WrongAnswerBookAdapter;
import com.smp.frontend.wrongAnswer.list.WrongAnswerBookItemData;
import com.smp.frontend.wrongAnswer.list.WrongAnswersAdapter;
import com.smp.frontend.wrongAnswer.list.WrongAnswersItemData;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WrongAnswersActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RetrofitClientWrongAnswer retrofitClientWrongAnswer;
    private WrongAnswerController wrongAnswerController;
    private WrongAnswersAdapter adapter;

    private String ID;
    private int find=0;
    private boolean stop= true;
    private ArrayList<WrongAnswersItemData> list = new ArrayList<>();
    private String choiceList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrong_answers);

        Intent intent = getIntent();
        ID = intent.getStringExtra("ID");


        recyclerView = findViewById(R.id.rv_WrongAnswers);
        retrofitClientWrongAnswer = RetrofitClientWrongAnswer.getInstance();
        wrongAnswerController = RetrofitClientWrongAnswer.getRetrofitInterface();
        Call<WrongAnswerResponseDto> req = wrongAnswerController.WrongAnswerBook(PreferencesManager.getString(getApplicationContext(),"token"));
        req.enqueue(new Callback<WrongAnswerResponseDto>() {
            @Override
            public void onResponse(Call<WrongAnswerResponseDto> call, Response<WrongAnswerResponseDto> response) {
                if(response.code() == 200) {
                    WrongAnswerResponseDto body = response.body();
                    int count = body.getCount();
                    List<?> data = body.getData();
                    gsonParsing instance = gsonParsing.getInstance();
                    while(stop) {
                        try {
                            String id = instance.jsonArray(data, find, "workbook", "id");
                            if(id.equals(ID)){
                                stop =false;
                                //qeustionList
                                for(int j =0; j<instance.jsonSizeTwo(data,find,"questionList");j++) {
                                    String qid = instance.jsonArrayTwo(data, find, "questionList", "id",j);
                                    String qtitle = instance.jsonArrayTwo(data, find, "questionList", "title",j);
                                    String qcontent = instance.jsonArrayTwo(data, find, "questionList", "content",j);
                                    System.out.println("questionList = " + qid + qtitle + qcontent);

                                    for(int k=0;k<instance.jsonSizeThree(data,find,"questionList","choiceList");k++){
                                        String cid = instance.jsonArrayThree(data,find,"questionList","choiceList","id",k);
                                        String cstate = instance.jsonArrayThree(data,find,"questionList","choiceList","state",k);
                                        String ccontent = instance.jsonArrayThree(data,find,"questionList","choiceList","content",k);
                                        System.out.println("chocieList = " + cid+ cstate + ccontent);
                                        choiceList += ccontent;
                                    }
                                    list= WrongAnswersItemData.createContactsList(instance.jsonSizeTwo(data,find,"questionList"),qid,qtitle,qcontent,choiceList);
                                    recyclerView.setHasFixedSize(true);
                                    adapter = new WrongAnswersAdapter(getApplicationContext(), list);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                    recyclerView.setAdapter(adapter);
                                    recyclerView.setHasFixedSize(true);
                                }
                            }
                            else {
                                find++;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<WrongAnswerResponseDto> call, Throwable t) {

            }
        });



    }
}