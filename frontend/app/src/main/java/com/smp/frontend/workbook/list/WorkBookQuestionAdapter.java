package com.smp.frontend.workbook.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.smp.frontend.R;
import com.smp.frontend.workbook.dto.WorkBookCheckRequestDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WorkBookQuestionAdapter extends RecyclerView.Adapter<WorkBookQuestionAdapter.Holder> {
    private List<WorkBookCheckRequestDto> checkWorkBookList = new ArrayList<>();
    private Context context;
    private List<WorkBookQuestionItemData> list = new ArrayList<>();
    private HashMap<Integer,Long> quesiton = new HashMap<>();
    private HashMap<Integer,Long> choice = new HashMap<>();
    private static long  id;
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

    /*
     * Todo 만들어진 ViewHolder에 data 삽입 ListView의 getView와 동일
     *
     * */
    @Override
    public void onBindViewHolder(Holder holder, int position) {
        // 각 위치에 문자열 세팅
        int itemposition = position;
        holder.onBind(list, position);


        id = list.get(itemposition).getId();
    }

    // 몇개의 데이터를 리스트로 뿌려줘야하는지 반드시 정의해줘야한다
    @Override
    public int getItemCount() {
        return list.size(); // RecyclerView의 size return
    }
    public int getListCount() {
        return quesiton.size(); // RecyclerView의 size return
    }
    public long getQeustionId(int i) {
        return quesiton.get(i);
    }
    public long getChoiceId(int i) {
        return choice.get(i);
    }


    // ViewHolder는 하나의 View를 보존하는 역할을 한다
    public class Holder extends RecyclerView.ViewHolder{
        private TextView title;
        private TextView content;
        private RadioGroup radioGroup;
        private RadioButton Rd_btn1,Rd_btn2,Rd_btn3,Rd_btn4;

        private Holder(View view){
            super(view);
            title = (TextView) view.findViewById(R.id.tv_title_wronganswers);
            content = (TextView) view.findViewById(R.id.tv_content);
            radioGroup = (RadioGroup)view.findViewById(R.id.RadioGroup_answers);
            Rd_btn1 = (RadioButton)view.findViewById(R.id.radio_btn1);
            Rd_btn2 = (RadioButton)view.findViewById(R.id.radio_btn2);
            Rd_btn3 = (RadioButton)view.findViewById(R.id.radio_btn3);
            Rd_btn4 = (RadioButton)view.findViewById(R.id.radio_btn4);


            }

        public void onBind(List<WorkBookQuestionItemData> list, int position) {
            long qId = list.get(position).getWorkBookQuestionListDto().getQuestionId();
            title.setText(Long.toString(qId));
            content.setText(list.get(position).getWorkBookQuestionListDto().getContent());
            if(list.get(position).getChoice1().size() <=0 ) {
                return;
            }
            else {
                try {
                    long ChocieId1 = list.get(position).getChoice1().get(position).getId();
                    long ChocieId2 = list.get(position).getChoice2().get(position).getId();
                    Rd_btn1.setText(Long.toString(ChocieId1));
                    Rd_btn2.setText(Long.toString(ChocieId2));
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        switch (i){
                            case R.id.radio_btn1 :
                                long ChocieId1 = list.get(position).getChoice1().get(position).getId();
                                quesiton.put(position,qId);
                                choice.put(position,ChocieId1);

                                break;
                            case R.id.radio_btn2 :
                                long ChocieId2 = list.get(position).getChoice2().get(position).getId();
                                quesiton.put(position,qId);
                                choice.put(position,ChocieId2);
                                break;
                        }
                    }
                });
            }

        }

    }
    }



