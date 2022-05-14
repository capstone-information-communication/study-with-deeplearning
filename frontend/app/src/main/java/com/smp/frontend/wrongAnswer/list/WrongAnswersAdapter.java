package com.smp.frontend.wrongAnswer.list;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.smp.frontend.R;
import com.smp.frontend.wrongAnswer.activity.WrongAnswersActivity;

import java.util.ArrayList;
import java.util.List;

public class WrongAnswersAdapter extends RecyclerView.Adapter<WrongAnswersAdapter.Holder> {

    private Context context;
    private List<WrongAnswersItemData> list = new ArrayList<>();
    private static int  id;
    public WrongAnswersAdapter(Context context, List<WrongAnswersItemData> list) {
        this.context = context;
        this.list = list;
    }

    // ViewHolder 생성
    // row layout을 화면에 뿌려주고 holder에 연결
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wrong_answers_view, parent, false);
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
        
        holder.title.setText(list.get(itemposition).getTitle());
        holder.content.setText(list.get(itemposition).getContent());
        holder.Rd_btn1.setText(list.get(itemposition).getChoiceList().get(0));
        holder.Rd_btn2.setText(list.get(itemposition).getChoiceList().get(1));

        id = list.get(itemposition).getId();
    }

    // 몇개의 데이터를 리스트로 뿌려줘야하는지 반드시 정의해줘야한다
    @Override
    public int getItemCount() {
        return list.size(); // RecyclerView의 size return
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
        }
    }



