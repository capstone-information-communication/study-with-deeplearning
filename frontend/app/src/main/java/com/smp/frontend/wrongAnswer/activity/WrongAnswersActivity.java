package com.smp.frontend.wrongAnswer.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WrongAnswersActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RetrofitClientWrongAnswer retrofitClientWrongAnswer;
    private WrongAnswerController wrongAnswerController;
    private WrongAnswersAdapter adapter;

    private int ID;
    private int find=0;
    private ArrayList<WrongAnswersItemData> list = new ArrayList<>();
    private List<String> choiceList = new ArrayList<>();

    private long backKeyPressedTime;
    Toast toast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrong_answers);

        Intent intent = getIntent();
        ID = intent.getIntExtra("ID",0);



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
                    for(int i=0; i<count;i++) {
                        try {
                            int id = Integer.parseInt(instance.jsonArray(data, i, "workbook", "id"));
                            if(id == ID){
                                //qeustionList
                                for(int j =0; j<instance.jsonSizeTwo(data,i,"questionList");j++) {
                                    int qid = Integer.parseInt(instance.jsonArrayTwo(data, i, "questionList", "id",j));
                                    String qtitle = instance.jsonArrayTwo(data, i, "questionList", "title",j);
                                    String qcontent = instance.jsonArrayTwo(data, i, "questionList", "content",j);
                                    System.out.println("questionList = " + qid + qtitle + qcontent);
                    
                                    for(int k=0;k<instance.jsonSizeThree(data,i,"questionList","choiceList",j);k++){
                                        System.out.println("j123123 = " +i+ " "+ j +" " + k);
                                        String cid = instance.jsonArrayThree(data,i,j,k,"questionList","choiceList","id");
                                        String cstate = instance.jsonArrayThree(data,i,j,k,"questionList","choiceList","state");
                                        String ccontent = instance.jsonArrayThree(data,i,j,k,"questionList","choiceList","content");
                                        System.out.println("ccontent = " + ccontent);
                                        choiceList.add(ccontent);

                                    }
                                    list.add(new WrongAnswersItemData(qid,qtitle,qcontent,choiceList));
                                    recyclerView.setHasFixedSize(true);
                                    adapter = new WrongAnswersAdapter(getApplicationContext(), list);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                    recyclerView.setAdapter(adapter);
                                    recyclerView.setHasFixedSize(true);
                                }
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
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finish();
            toast.cancel();
        }
    }
}