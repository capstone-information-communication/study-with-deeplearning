package com.smp.frontend.workbook.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.smp.frontend.common.PreferencesManager;
import com.smp.frontend.R;
import com.smp.frontend.common.WorkBookQuestionListDto;
import com.smp.frontend.common.choiceListDto;
import com.smp.frontend.common.gsonParsing;
import com.smp.frontend.member.activity.MainActivity;
import com.smp.frontend.workbook.RetrofitClientWorkbook;
import com.smp.frontend.workbook.WorkbookController;
import com.smp.frontend.workbook.dto.WorkBookCheckRequestDto;
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
    private int ID,page;
    private RecyclerView recyclerView;
    private List<WorkBookQuestionItemData> list= new ArrayList<>();;
    private WorkBookQuestionAdapter adapter;
    private List<choiceListDto> choiceListDtoList1 = new ArrayList<>();
    private List<choiceListDto> choiceListDtoList2 = new ArrayList<>();
    private List<choiceListDto> choiceListDtoList3 = new ArrayList<>();
    private List<choiceListDto> choiceListDtoList4 = new ArrayList<>();
    private List<WorkBookCheckRequestDto> WorkBookRequest = new ArrayList<>();
    private Button check_btn;
    private Toast toast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_book_qeustion);

        Intent intent = getIntent();
        ID = intent.getIntExtra("ID",0);
        page = intent.getIntExtra("page",0);
        recyclerView = findViewById(R.id.rv_WrongAnswers);
        getQeustion();
        check_btn = findViewById(R.id.check_btn);
        check_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0;i<adapter.getItemCount();i++){
                    System.out.println("adapter.getListCount() = " + adapter.getListCount());
                    System.out.println("adapter.getItemCount() = " + adapter.getItemCount());
                    if(adapter.getListCount() == 0 || adapter.getListCount() != adapter.getItemCount()){
                        System.out.println("빈칸이거나 선택하지않은 문제가 있습니다.");
                        return;
                    }
                    WorkBookRequest.add(i,new WorkBookCheckRequestDto(adapter.getQeustionId(i),adapter.getChoiceId(i)));
                }
                RetrofitClientWorkbook retrofitClientWorkbook = RetrofitClientWorkbook.getInstance();
                WorkbookController workbookController = RetrofitClientWorkbook.getRetrofitInterface();
                Call<WorkBookTestResponse> request = workbookController.WorkBookCheck(PreferencesManager.getString(getApplicationContext(),"token"),
                        ID,WorkBookRequest
                );
                request.enqueue(new Callback<WorkBookTestResponse>() {
                    @Override
                    public void onResponse(Call<WorkBookTestResponse> call, Response<WorkBookTestResponse> response) {
                        if(response.code() == 200){
                            WorkBookTestResponse data =  response.body();
                            toast.makeText(getApplicationContext(),"answer : "+data.getAnswer() +" worng : " + data.getWrong() ,Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<WorkBookTestResponse> call, Throwable t) {

                    }
                });
            }
        });

    }

    public void getQeustion(){
        RetrofitClientWorkbook retrofitClientWorkbook = RetrofitClientWorkbook.getInstance();
        WorkbookController workbookController = RetrofitClientWorkbook.getRetrofitInterface();
        Call<WorkBookResponseDto> responseCall = workbookController.getWorkbook(PreferencesManager.getString(getApplicationContext(),"token"),page);
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
                                    long qid = parsing2.getId();
                                    String qtitle = parsing2.getTitle();
                                    String qcontent = parsing2.getContent();
                                    WorkBookQuestionListDto WorkBookQuestionListDto = new WorkBookQuestionListDto(qid,qtitle, qcontent);
                                    //chocieList
                                    for (int k = 0; k < parsing2.getChoiceList().size(); k++) {

                                        WorkBookTestResponse parsing3 = (WorkBookTestResponse) instance.parsing(
                                                instance.ArrToString(instance.toJsonArr(parsing2.getChoiceList()), k),
                                                WorkBookTestResponse.class
                                        );
                                        long ChoiceId = parsing3.getId();
                                        String ChoiceState = parsing3.getState();
                                        String ChoiceContent = parsing3.getContent();

                                        if(k ==0) {
                                            choiceListDtoList1.add(new choiceListDto(ChoiceId, ChoiceState, ChoiceContent));
                                        }
                                        else if(k==1 ){
                                            choiceListDtoList2.add(new choiceListDto(ChoiceId, ChoiceState, ChoiceContent));
                                        }
/*                                        else if(k==2){
                                            choiceListDtoList3.add(new choiceListDto(ChoiceId, ChoiceState, ChoiceContent));
                                        }
                                        else if(k==3){
                                            choiceListDtoList4.add(new choiceListDto(ChoiceId, ChoiceState, ChoiceContent));
                                        }*/
                                    }
                                    list.add(new WorkBookQuestionItemData(id, WorkBookQuestionListDto, choiceListDtoList1,choiceListDtoList2));
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