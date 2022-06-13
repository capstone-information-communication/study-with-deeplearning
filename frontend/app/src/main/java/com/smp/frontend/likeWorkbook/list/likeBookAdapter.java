package com.smp.frontend.likeWorkbook.list;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.smp.frontend.R;
import com.smp.frontend.workbook.activity.WorkBookQeustion;
import com.smp.frontend.wrongAnswer.activity.WrongAnswersActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class likeBookAdapter extends RecyclerView.Adapter<likeBookAdapter.Holder> {

    private Context context;
    private List<likeBookItemData> list = new ArrayList<>();
    private int id;
    Map<Integer, Integer> intentId = new HashMap<>();
    Map<Integer, Integer> intentPage = new HashMap<>();
    Map<Integer, String> intentTitle = new HashMap<>();
    Map<Integer, String> intentDescription = new HashMap<>();
    Map<Integer, Boolean> intentSearch = new HashMap<>();
    private int itemposition;
    Intent intent;
    public likeBookAdapter(Context context, List<likeBookItemData> list) {
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
        itemposition = position;
        holder.wordText.setText(list.get(itemposition).getTitle());
        holder.meaningText.setText(list.get(itemposition).getDescription());
        id =  list.get(itemposition).getId();
        intentId.put(itemposition,id);
        intentPage.put(itemposition,list.get(itemposition).getPage());
    }

    // 몇개의 데이터를 리스트로 뿌려줘야하는지 반드시 정의해줘야한다
    @Override
    public int getItemCount() {
        return list.size(); // RecyclerView의 size return
    }

    public void removeItem(int absoluteAdapterPosition) {
        list.remove(absoluteAdapterPosition);
        notifyItemRemoved(absoluteAdapterPosition);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
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
                        intent = new Intent(context, WorkBookQeustion.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("ID", intentId.get(pos));
                        intent.putExtra("page", intentPage.get(pos));
                        intent.putExtra("likeBook",true);
                        context.startActivity(intent);
                    }
                }
            });

            }
        }
    }



