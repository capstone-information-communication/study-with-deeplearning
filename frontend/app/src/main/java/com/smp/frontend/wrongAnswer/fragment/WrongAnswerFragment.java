    package com.smp.frontend.wrongAnswer.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smp.frontend.PreferencesManager;
import com.smp.frontend.R;
import com.smp.frontend.restAPi.gsonParsing;
import com.smp.frontend.wrongAnswer.RetrofitClientWrongAnswer;
import com.smp.frontend.wrongAnswer.WrongAnswerController;
import com.smp.frontend.wrongAnswer.dto.WrongAnswerResponseDto;
import com.smp.frontend.wrongAnswer.dto.WrongAnswerTestResponse;
import com.smp.frontend.wrongAnswer.list.WrongAnswerBookAdapter;
import com.smp.frontend.wrongAnswer.list.WrongAnswerBookItemData;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WrongAnswerFragment extends Fragment {
    private RecyclerView recyclerView;
    private WrongAnswerBookAdapter adapter;
    private ArrayList<WrongAnswerBookItemData> list = new ArrayList<>();
    RetrofitClientWrongAnswer retrofitClient;
    WrongAnswerController wrongAnswerController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wrong_answer, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.rv_WrongAnswerBook);
        
        retrofitClient = RetrofitClientWrongAnswer.getInstance();
        wrongAnswerController = RetrofitClientWrongAnswer.getRetrofitInterface();
        Call<WrongAnswerResponseDto> request = wrongAnswerController.WrongAnswerBook(PreferencesManager.getString(getActivity().getApplicationContext(),"token"));
        request.enqueue(new Callback<WrongAnswerResponseDto>() {
            @Override
            public void onResponse(Call<WrongAnswerResponseDto> call, Response<WrongAnswerResponseDto> response) {
                if(response.code() == 200){
                    WrongAnswerResponseDto body = response.body();
                    int count = body.getCount();
                    List<?> data = body.getData();
                    gsonParsing instance = gsonParsing.getInstance();

                    for (int i = 0; i < count; i++) {
                        try {
                            //workbook JSON
                            System.out.println("data = " + data);
                            WrongAnswerTestResponse parsingDto = (WrongAnswerTestResponse)instance.parsing(
                              instance.GetStringJSON(instance.toJsonArr(data),i,"workbook"),
                                    WrongAnswerTestResponse.class
                            );
                            long id = (parsingDto.getId());
                            String title = parsingDto.getTitle();
                            String description = parsingDto.getDescription();

                            list.add(new WrongAnswerBookItemData(id,title,description));
                            recyclerView.setHasFixedSize(true);
                            adapter = new WrongAnswerBookAdapter(getActivity(), list);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                            recyclerView.setAdapter(adapter);
                            recyclerView.setHasFixedSize(true);

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

        // Inflate the layout for this fragment
        return view;
    }
    @Override
    public void onStop() {
        super.onStop();
        list = new ArrayList<>();
    }
}