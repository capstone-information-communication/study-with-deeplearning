package com.smp.frontend.workbook.list;

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

public class WorkBookAdapter extends RecyclerView.Adapter<WorkBookAdapter.Holder> {

    private Context context;
    private List<WorkBookItemData> list = new ArrayList<>();
    private int id;
    Map<Integer, Integer> a = new HashMap<>();

    Intent intent;
    public WorkBookAdapter(Context context, List<WorkBookItemData> list) {
        this.context = context;
        this.list = list;
    }

    // ViewHolder 생성
    // row layout을 화면에 뿌려주고 holder에 연결
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workbook_view, parent, false);
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
        holder.tv_title_workbook.setText(list.get(itemposition).getTitle());
        holder.tv_description_workbook.setText(list.get(itemposition).getdescription());
        id =  list.get(itemposition).getId();
        a.put(itemposition,id);

    }

    // 몇개의 데이터를 리스트로 뿌려줘야하는지 반드시 정의해줘야한다
    @Override
    public int getItemCount() {
        return list.size(); // RecyclerView의 size return
    }

    // ViewHolder는 하나의 View를 보존하는 역할을 한다
    public class Holder extends RecyclerView.ViewHolder{
        private TextView tv_title_workbook;
        private TextView tv_description_workbook;

        public Holder(View view){
            super(view);
            tv_title_workbook = (TextView) view.findViewById(R.id.tv_title_workbook);
            tv_description_workbook = (TextView) view.findViewById(R.id.tv_description_workbook);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getBindingAdapterPosition();

                    if(pos != RecyclerView.NO_POSITION) {
                        intent = new Intent(context, WorkBookQeustion.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("ID",a.get(pos));
                        System.out.println("a.get(pos) = " + a.get(pos));
                        context.startActivity(intent);
                    }
                }
            });
            }
        }
    }



