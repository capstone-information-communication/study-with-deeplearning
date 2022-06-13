package com.smp.frontend.workbook.list;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.smp.frontend.R;
import com.smp.frontend.global.gsonParsing;
import com.smp.frontend.workbook.dto.WorkBookTestResponse;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WorkBookQuestionAdapter extends RecyclerView.Adapter<WorkBookQuestionAdapter.Holder> {
    private Context context;
    private List<WorkBookQuestionItemData> list = new ArrayList<>();
    private HashMap<Integer, Long> quesiton = new HashMap<>();
    private HashMap<Integer, String> choice = new HashMap<>();
    private int itemposition;
    private String State;
    private HashMap<Integer, String> choiceState = new HashMap<>();
    private static long id;

    public WorkBookQuestionAdapter(Context context, List<WorkBookQuestionItemData> list) {
        this.context = context;
        this.list = list;
    }

    // ViewHolder 생성
    // row layout을 화면에 뿌려주고 holder에 연결
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workbook_qeustion_view, parent, false);

        Holder holder = new Holder(view);
        return holder;
    }

    private TextView title;
    private TextView content;
    private RadioGroup radioGroup;
    private RadioButton Rd_btn1, Rd_btn2, Rd_btn3, Rd_btn4;
    private EditText Ed_text;

    /*
     * Todo 만들어진 ViewHolder에 data 삽입 ListView의 getView와 동일
     *
     * */
    @Override
    public void onBindViewHolder(Holder holder, @SuppressLint("RecyclerView") int position) {
        // 각 위치에 문자열 세팅
        itemposition = position;
        id = list.get(position).getId();
        holder.onBind(list, itemposition);
    }

    // 몇개의 데이터를 리스트로 뿌려줘야하는지 반드시 정의해줘야한다
    @Override
    public int getItemCount() {
        return list.size(); // RecyclerView의 size return
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public HashMap<Integer, Long> getQuestion() {
        return quesiton;
    }

    public int getChoiceCount() {
        return choice.size(); // RecyclerView의 size return
    }

    public HashMap<Integer, String> getChoice() {
        return choice;
    }



    // ViewHolder는 하나의 View를 보존하는 역할을 한다
    public class Holder extends RecyclerView.ViewHolder {
        private String shortAnswer;

        private Holder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.tv_title_wronganswers);
            content = (TextView) view.findViewById(R.id.tv_content);
            radioGroup = (RadioGroup) view.findViewById(R.id.RadioGroup_answers);
            Rd_btn1 = (RadioButton) view.findViewById(R.id.radio_btn1);
            Rd_btn2 = (RadioButton) view.findViewById(R.id.radio_btn2);
            Rd_btn3 = (RadioButton) view.findViewById(R.id.radio_btn3);
            Rd_btn4 = (RadioButton) view.findViewById(R.id.radio_btn4);
            Ed_text = (EditText) view.findViewById(R.id.Ed_Short);
        }

        public void onBind(List<WorkBookQuestionItemData> list, int itemposition) {
            //qeustionList
            long qId = list.get(itemposition).getWorkBookQuestionListDto().getQuestionId();
            String category = list.get(itemposition).getWorkBookQuestionListDto().getCategory();
            String QTitle = list.get(itemposition).getWorkBookQuestionListDto().getTitle();
            String QContent = list.get(itemposition).getWorkBookQuestionListDto().getContent();
             List<Long> choiceListId = new ArrayList<>();
             List<String> choiceList = new ArrayList<>();
            //chocieList
            List<?> parsing = list.get(itemposition).getChoiceList();
            int size = list.get(itemposition).getChoiceList().size();
            quesiton.put(itemposition, qId);
            gsonParsing instance = gsonParsing.getInstance();
            try {
                for (int i = 0; i < size; i++) {
                    WorkBookTestResponse choiceParsing = (WorkBookTestResponse) instance.parsing(
                            instance.ArrToString(instance.toJsonArr(parsing), i),
                            WorkBookTestResponse.class
                    );
                    choiceListId.add(choiceParsing.getId());
                    choiceList.add(choiceParsing.getContent());
                    State = choiceParsing.getState();
                    choiceState.put(i, State);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            title.setText(QTitle);
            content.setText(QContent);
            if (size == 0) {
                Rd_btn1.setVisibility(View.GONE);
                Rd_btn2.setVisibility(View.GONE);
                Rd_btn3.setVisibility(View.GONE);
                Rd_btn4.setVisibility(View.GONE);
                Ed_text.setVisibility(View.GONE);
                return;
            } else {
                try {
                    if (category.equals("BLANK") || category.equals("SHORT")) {
                        Rd_btn1.setVisibility(View.GONE);
                        Rd_btn2.setVisibility(View.GONE);
                        Rd_btn3.setVisibility(View.GONE);
                        Rd_btn4.setVisibility(View.GONE);
                        Ed_text.setVisibility(View.VISIBLE);
                        quesiton.put(itemposition, qId);
                        Ed_text.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                shortAnswer = charSequence.toString();
                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                shortAnswer = charSequence.toString();
                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                System.out.println("choiceList = " + choiceList.get(0));
                                System.out.println("itemposition = " + itemposition);
                                choice.put(itemposition, "선택");
                                if (shortAnswer.equals(choiceList.get(0))) {
                                    System.out.println("shortAnswer = " + shortAnswer);
                                    quesiton.remove(itemposition);
                                } else {
                                    System.out.println("틀린 답 입력");
                                }
                            }
                        });

                    } else if (category.equals("MULTIPLE") || category.equals("ORDER")) {
                        Ed_text.setVisibility(View.GONE);
                        Rd_btn1.setVisibility(View.VISIBLE);
                        Rd_btn2.setVisibility(View.VISIBLE);
                        Rd_btn3.setVisibility(View.VISIBLE);
                        Rd_btn4.setVisibility(View.VISIBLE);


                        Rd_btn1.setText(choiceList.get(0));
                        Rd_btn2.setText(choiceList.get(1));
                        Rd_btn3.setText(choiceList.get(2));
                        Rd_btn4.setText(choiceList.get(3));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        choice.put(itemposition, "선택");
                        System.out.println("itemposition = " + itemposition);
                        switch (i) {
                            case R.id.radio_btn1:
                                if (choiceState.get(0).equals("WRONG")) {
                                    long ChocieId1 = choiceListId.get(0);
                                    System.out.println("ChocieId1 = " + ChocieId1);
                                    quesiton.put(itemposition, qId);
                                } else {
                                    quesiton.remove(itemposition);
                                }
                                break;
                            case R.id.radio_btn2:
                                if (choiceState.get(1).equals("WRONG")) {
                                    long ChocieId2 = choiceListId.get(1);
                                    System.out.println("ChocieId2 = " + ChocieId2);
                                    quesiton.put(itemposition, qId);
                                } else {
                                    quesiton.remove(itemposition);
                                }
                                break;
                            case R.id.radio_btn3:
                                if (choiceState.get(2).equals("WRONG")) {
                                    long ChocieId3 = choiceListId.get(2);
                                    System.out.println("ChocieId3 = " + ChocieId3);
                                    quesiton.put(itemposition, qId);
                                } else {
                                    quesiton.remove(itemposition);
                                }
                                break;
                            case R.id.radio_btn4:
                                if (choiceState.get(3).equals("WRONG")) {
                                    long ChocieId4 = choiceListId.get(3);
                                    System.out.println("ChocieId4 = " + ChocieId4);
                                    quesiton.put(itemposition, qId);
                                } else {
                                    quesiton.remove(itemposition);
                                }
                                break;
                        }
                    }
                });
            }

        }

    }
}



