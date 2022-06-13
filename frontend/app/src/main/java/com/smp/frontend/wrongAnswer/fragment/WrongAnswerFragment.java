    package com.smp.frontend.wrongAnswer.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.smp.frontend.global.PreferencesManager;
import com.smp.frontend.R;
import com.smp.frontend.global.gsonParsing;
import com.smp.frontend.member.MemberController;
import com.smp.frontend.member.dto.MemberSginInResponseDto;
import com.smp.frontend.member.dto.MemberSignInRequestDto;
import com.smp.frontend.workbook.RetrofitClientWorkbook;
import com.smp.frontend.workbook.WorkbookController;
import com.smp.frontend.workbook.dto.WorkBookResponseDto;
import com.smp.frontend.workbook.fragment.WorkBookFragment;
import com.smp.frontend.workbook.list.RecyclerItemTouchHelper;
import com.smp.frontend.workbook.list.WorkBookAdapter;
import com.smp.frontend.workbook.list.WorkBookItemData;
import com.smp.frontend.wrongAnswer.RetrofitClientWrongAnswer;
import com.smp.frontend.wrongAnswer.WrongAnswerController;
import com.smp.frontend.wrongAnswer.dto.DeleteWrongAnswerResponseDto;
import com.smp.frontend.wrongAnswer.dto.WrongAnswerResponseDto;
import com.smp.frontend.wrongAnswer.dto.WrongAnswerTestResponse;
import com.smp.frontend.wrongAnswer.list.RecyclerItemTouchHelperWrong;
import com.smp.frontend.wrongAnswer.list.WrongAnswerBookAdapter;
import com.smp.frontend.wrongAnswer.list.WrongAnswerBookItemData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WrongAnswerFragment extends Fragment implements RecyclerItemTouchHelperWrong.RecyclerItemTouchHelperListener {
    private RecyclerView recyclerView;
    private WrongAnswerBookAdapter adapter;
    private ArrayList<WrongAnswerBookItemData> list = new ArrayList<>();
    RetrofitClientWrongAnswer retrofitClient = RetrofitClientWrongAnswer.getInstance();
    WrongAnswerController wrongAnswerController = RetrofitClientWrongAnswer.getRetrofitInterface();

    private PieChart pieChart;
    private int getblank, getmutiple, getorder , getshort ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public static WrongAnswerFragment newInstance(int number) {
        WrongAnswerFragment WrongAnswerFragment = new WrongAnswerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("number", number);
        WrongAnswerFragment.setArguments(bundle);
        return WrongAnswerFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wrong_answer, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.rv_WrongAnswerBook);
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelperWrong(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        pieChart = view.findViewById(R.id.pieChart);
        // Inflate the layout for this fragment
        return view;
    }
    @Override
    public void onStop() {
        super.onStop();
        list = new ArrayList<>();
    }

    @Override
    public void onResume() {
        super.onResume();
        list = new ArrayList<>();
        RetrofitStart();
        getMembers();
    }

    public void RetrofitStart(){

        Call<WrongAnswerResponseDto> request = wrongAnswerController.WrongAnswerBook(PreferencesManager.getString(getActivity().getApplicationContext(),"token"));
        request.enqueue(new Callback<WrongAnswerResponseDto>() {
            @Override
            public void onResponse(Call<WrongAnswerResponseDto> call, Response<WrongAnswerResponseDto> response) {
                if(response.code() == 200){
                    WrongAnswerResponseDto body = response.body();
                    int count = body.getCount();
                    List<?> data = body.getData();
                    gsonParsing instance = gsonParsing.getInstance();
                    if(data.size() == 0){
                        list = new ArrayList<>();
                        adapter = new WrongAnswerBookAdapter(getActivity(), list);
                        recyclerView.setAdapter(adapter);
                    }
                        for (int i = 0; i < count; i++) {
                            try {
                                //workbook JSON
                                System.out.println("data = " + data);
                                WrongAnswerTestResponse parsingDto = (WrongAnswerTestResponse) instance.parsing(
                                        instance.toJson(data.get(i)),
                                        WrongAnswerTestResponse.class
                                );
                                long id = (parsingDto.getId());
                                String title = parsingDto.getTitle();
                                String description = parsingDto.getDescription();

                                list.add(new WrongAnswerBookItemData(id, title, description));
                                recyclerView.setHasFixedSize(true);
                                adapter = new WrongAnswerBookAdapter(getActivity(), list);
                                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                recyclerView.setAdapter(adapter);
                                recyclerView.setHasFixedSize(true);

                            } catch (Exception e) {
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
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof WrongAnswerBookAdapter.Holder) {
            // get the removed item name to display it in snack bar
            String name = list.get(viewHolder.getAbsoluteAdapterPosition()).getTitle();
            long WorkBookId = list.get(viewHolder.getAbsoluteAdapterPosition()).getId();
            System.out.println("WorkBookId = " + WorkBookId);
            // backup of removed item for undo purpose
            WrongAnswerBookItemData deletedItem = list.get(viewHolder.getAbsoluteAdapterPosition());
            int deletedIndex = viewHolder.getAbsoluteAdapterPosition();

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    getContext());
            alertDialogBuilder.setTitle(name);
            alertDialogBuilder
                    .setMessage("정말로 이 문제집을 삭제할까요?")
                    .setCancelable(false)
                    .setPositiveButton("예",
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialog, int id) {
                                    Call<DeleteWrongAnswerResponseDto> responseCall = wrongAnswerController.DeletWrongAnswer(PreferencesManager.getString(getContext(),"token"),WorkBookId);
                                    responseCall.enqueue(new Callback<DeleteWrongAnswerResponseDto>() {
                                        @Override
                                        public void onResponse(Call<DeleteWrongAnswerResponseDto> call, Response<DeleteWrongAnswerResponseDto> response) {
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
                                                Toast.makeText(getContext(),"삭제할수 없습니다.",Toast.LENGTH_LONG).show();
                                                adapter.notifyDataSetChanged();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<DeleteWrongAnswerResponseDto> call, Throwable t) {

                                        }
                                    });

                                }
                            })
                    .setNegativeButton("취소",
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
    public void  getMembers(){

        Call<MemberSginInResponseDto> member = wrongAnswerController.GetUser(PreferencesManager.getString(getActivity(),"token"));
        member.enqueue(new Callback<MemberSginInResponseDto>() {
            @Override
            public void onResponse(Call<MemberSginInResponseDto> call, Response<MemberSginInResponseDto> response) {
                MemberSginInResponseDto body = response.body();
                ArrayList NoOfEmp = new ArrayList();
                ArrayList name = new ArrayList();

                MemberSginInResponseDto  afa= body.getWrongFigure();
                getblank = afa.getBlankCount();
                getmutiple = afa.getMultipleCount();
                getorder = afa.getOrderCount();
                getshort = afa.getShortCount();

                if(getblank != 0) {
                    NoOfEmp.add(new Entry(getblank,0));
                    name.add("빈칸");
                }
                if(getmutiple != 0){
                    NoOfEmp.add(new Entry(getmutiple,1));
                    name.add("객관");

                }
                if(getorder != 0) {
                    NoOfEmp.add(new Entry(getorder,2));
                    name.add("주관");

                }
                if(getshort != 0) {
                    NoOfEmp.add(new Entry(getshort,3));
                    name.add("단답");
                }

                pieChart.setExtraOffsets(5,10,5,5);



                pieChart.setDrawHoleEnabled(false);
                pieChart.setHoleColor(Color.WHITE);
                pieChart.setTransparentCircleRadius(61f);

                PieDataSet dataSet = new PieDataSet(NoOfEmp,"문제");

                pieChart.setDescription("오답 유형");


                dataSet.setSliceSpace(3f);
                dataSet.setSelectionShift(5f);

                dataSet.setColors(ColorTemplate.LIBERTY_COLORS);
                pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic); //애니메이션


                PieData data = new PieData(name, dataSet);          // MPAndroidChart v3.X 오류 발생
                data.setValueTextSize(15);
                pieChart.setData(data);


            }

            @Override
            public void onFailure(Call<MemberSginInResponseDto> call, Throwable t) {

            }
        });
    }

}