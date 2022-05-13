package com.smp.frontend.wrongAnswer.list;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.smp.frontend.R;
import com.smp.frontend.wrongAnswer.activity.WrongAnswersActivity;

import java.util.ArrayList;
import java.util.List;

public class WrongAnswerBookAdapter extends RecyclerView.Adapter<WrongAnswerBookAdapter.Holder> {

    private Context context;
    private List<WrongAnswerBookItemData> list = new ArrayList<>();
    private static String  id;
    public WrongAnswerBookAdapter(Context context, List<WrongAnswerBookItemData> list) {
        this.context = context;
        this.list = list;
    }

    // ViewHolder 생성
    // row layout을 화면에 뿌려주고 holder에 연결
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wrong_answer_workbook_view, parent, false);
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
        holder.wordText.setText(list.get(itemposition).getTitle());
        holder.meaningText.setText(list.get(itemposition).getDescription());
        id = list.get(itemposition).getId();
    }

    // 몇개의 데이터를 리스트로 뿌려줘야하는지 반드시 정의해줘야한다
    @Override
    public int getItemCount() {
        return list.size(); // RecyclerView의 size return
    }

    // ViewHolder는 하나의 View를 보존하는 역할을 한다
    public class Holder extends RecyclerView.ViewHolder{
        public TextView wordText;
        public TextView meaningText;

        public Holder(View view){
            super(view);
            wordText = (TextView) view.findViewById(R.id.tv_title_wrongAnswerbook);
            meaningText = (TextView) view.findViewById(R.id.tv_description_WrongAnswers);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getBindingAdapterPosition();

                    if(pos != RecyclerView.NO_POSITION) {
                        Intent intent = new Intent(context, WrongAnswersActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("ID",id);
                        context.startActivity(intent);
                    }
                }
            });
            }
        }
    }



