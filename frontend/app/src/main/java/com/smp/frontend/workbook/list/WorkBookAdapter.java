package com.smp.frontend.workbook.list;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.smp.frontend.R;
import com.smp.frontend.global.PreferencesManager;
import com.smp.frontend.global.gsonParsing;
import com.smp.frontend.workbook.RetrofitClientWorkbook;
import com.smp.frontend.workbook.WorkbookController;
import com.smp.frontend.workbook.activity.WorkBookQeustion;
import com.smp.frontend.workbook.dto.WorkBookResponseDto;
import com.smp.frontend.workbook.dto.WorkBookTestResponse;
import com.smp.frontend.workbook.dto.likeWorkBook;
import com.smp.frontend.wrongAnswer.activity.WrongAnswersActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.Path;

public class WorkBookAdapter extends RecyclerView.Adapter<WorkBookAdapter.Holder> {
    private Context context;
    private List<WorkBookItemData> list = new ArrayList<>();
    private int id,itemposition;
    Map<Integer, Integer> intentId = new HashMap<>();
    Map<Integer, Integer> intentPage = new HashMap<>();
    Map<Integer, String> intentTitle = new HashMap<>();
    Map<Integer, String> intentDescription = new HashMap<>();
    Map<Integer, Boolean> intentSearch = new HashMap<>();
    Map<Integer,Integer> likecount = new HashMap<>();
    private Map<Long,Boolean> likeMap = new HashMap<>();

    Intent intent;
    private RetrofitClientWorkbook retrofitClientWorkbook = RetrofitClientWorkbook.getInstance();
    private WorkbookController workbookController = RetrofitClientWorkbook.getRetrofitInterface();
    private int page = 0;

    public WorkBookAdapter(Context context, List<WorkBookItemData> list) {
        this.context = context;
        this.list = list;
    }

    // ViewHolder 생성
    // row layout을 화면에 뿌려주고 holder에 연결
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workbook_view, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    public Map<Integer, Integer> getIntentId() {
        return intentId;
    }

    public int getItemposition() {
        return itemposition;
    }

    /*
     * Todo 만들어진 ViewHolder에 data 삽입 ListView의 getView와 동일
     *
     * */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(Holder holder, int position) {
        // 각 위치에 문자열 세팅
        itemposition = position;
        holder.tv_title_workbook.setText(list.get(itemposition).getTitle());
        holder.tv_description_workbook.setText(list.get(itemposition).getdescription());
        likecount.put(itemposition,list.get(itemposition).getLikeCount());
        holder.cntLike.setText(likecount.get(itemposition).toString());
        holder.likeView.setImageDrawable(list.get(position).getDrawable());
        id =  list.get(itemposition).getId();
        intentId.put(itemposition,id);
        intentPage.put(itemposition,list.get(itemposition).getPage());
        intentTitle.put(itemposition,list.get(itemposition).getTitle());
        intentDescription.put(itemposition,list.get(itemposition).getdescription());
        intentSearch.put(itemposition,list.get(itemposition).isSearch());
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

    public void restoreItem(WorkBookItemData item, int absoluteAdapterPosition) {
        list.add(absoluteAdapterPosition, item);
        notifyItemChanged(absoluteAdapterPosition);
    }

    // ViewHolder는 하나의 View를 보존하는 역할을 한다
    public class Holder extends RecyclerView.ViewHolder {
        private TextView tv_title_workbook, tv_description_workbook , cntLike;
        private ImageView likeView;
        private String a;

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public Holder(View view) {
            super(view);
            tv_title_workbook = (TextView) view.findViewById(R.id.tv_title_workbook);
            tv_description_workbook = (TextView) view.findViewById(R.id.tv_description_workbook);
            likeView = (ImageView) view.findViewById(R.id.like);
            cntLike = (TextView) view.findViewById(R.id.likeCount);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getBindingAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION) {
                        intent = new Intent(context, WorkBookQeustion.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("ID", intentId.get(pos));
                        intent.putExtra("page", intentPage.get(pos));
                        intent.putExtra("title", intentTitle.get(pos));
                        intent.putExtra("description", intentDescription.get(pos));
                        intent.putExtra("search", intentSearch.get(pos));
                        context.startActivity(intent);
                    }
                }
            });
            likeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sumbitLike();
                }
            });
        }


        public void sumbitLike(){
                Call<WorkBookTestResponse> checklike = workbookController.likeWorkBook(PreferencesManager.getString(itemView.getContext(),"token"),
                        new likeWorkBook(intentId.get(getAbsoluteAdapterPosition())));

                checklike.enqueue(new Callback<WorkBookTestResponse>() {
                    @Override
                    public void onResponse(Call<WorkBookTestResponse> call, Response<WorkBookTestResponse> response) {

                        if(response.code() == 400){
                            Call<WorkBookTestResponse> deletelike = workbookController.deleteLikeWorkBook(PreferencesManager.getString(itemView.getContext(),"token"),
                                    intentId.get(getAbsoluteAdapterPosition()));
                            deletelike.enqueue(new Callback<WorkBookTestResponse>() {
                                @Override
                                public void onResponse(Call<WorkBookTestResponse> call, Response<WorkBookTestResponse> response2) {
                                    int a = likecount.get(getAbsoluteAdapterPosition());
                                    cntLike.setText(Integer.toString(a));
                                    System.out.println("response2.code() = " + response2.code());
                                    System.out.println("response2 = " + response2.errorBody());
                                }

                                @Override
                                public void onFailure(Call<WorkBookTestResponse> call, Throwable t) {

                                }
                            });
                        }
                        else if(response.code() == 201){
                            likeView.setImageResource(R.drawable.ic_baseline_favorite_24);
                            int a = likecount.get(getAbsoluteAdapterPosition());
                            a++;
                            cntLike.setText(Integer.toString(a));
                        }
                        else {
                            likeView.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                        }
                    }
                    @Override
                    public void onFailure(Call<WorkBookTestResponse> call, Throwable t) {

                    }
                });
            }
    }
}



