package com.smp.frontend.wrongAnswer.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.smp.frontend.common.PreferencesManager;
import com.smp.frontend.R;
import com.smp.frontend.common.choiceListDto;
import com.smp.frontend.common.gsonParsing;
import com.smp.frontend.common.WrongAnswerQuestionListDto;
import com.smp.frontend.wrongAnswer.RetrofitClientWrongAnswer;
import com.smp.frontend.wrongAnswer.WrongAnswerController;
import com.smp.frontend.wrongAnswer.dto.WrongAnswerResponseDto;
import com.smp.frontend.wrongAnswer.dto.WrongAnswerTestResponse;
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

    private long ID;
    private ArrayList<WrongAnswersItemData> list = new ArrayList<>();

    @Override
    public void onBackPressed() {
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrong_answers);

        Intent intent = getIntent();
        ID = intent.getLongExtra("ID",0);

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
                            //workbook 파싱
                            WrongAnswerTestResponse parsing = (WrongAnswerTestResponse)instance.parsing(
                                    instance.toJson(data.get(i)),
                                    WrongAnswerTestResponse.class
                            );
                            System.out.println("parsing = " + parsing.getTitle());
                            long id = parsing.getId();
                            if(id == ID){
                                //qeustionList
                                for(int j=0;j<parsing.getWrongAnswerQuestionList().size();j++) {
                                    WrongAnswerTestResponse parsing2 = (WrongAnswerTestResponse) instance.parsing(
                                            instance.ArrToString(instance.toJsonArr(parsing.getWrongAnswerQuestionList()), j),
                                            WrongAnswerTestResponse.class
                                    );
                                    long qid = parsing2.getQuestionId();
                                    long wrongid = parsing2.getWrongAnswerId();
                                    String qtitle = parsing2.getTitle();
                                    String qcontent = parsing2.getContent();
                                    String category = parsing2.getCategory();

                                    //코멘트 가져오기 나중에 수정 필요해보임
                                    WrongAnswerTestResponse commentry = (WrongAnswerTestResponse) instance.parsing(
                                            instance.toJson(parsing2.getCommentary()), WrongAnswerTestResponse.class);
                                    String qComment = commentry.getComment();
                                    WrongAnswerQuestionListDto QeustionListClass = new WrongAnswerQuestionListDto(qid,wrongid,qtitle, qcontent, qComment,category);
                                    //choiceList

                                    List<?> choiceList = parsing2.getChoiceList();

                                    list.add(new WrongAnswersItemData(id,QeustionListClass, choiceList));
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
}