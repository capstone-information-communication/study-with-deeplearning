package com.smp.frontend.wrongAnswer.list;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.smp.frontend.R;

import java.util.ArrayList;
import java.util.List;

public class WrongAnswersAdapter extends RecyclerView.Adapter<WrongAnswersAdapter.Holder> {

    private Context context;
    private List<WrongAnswersItemData> list = new ArrayList<>();
    private SparseBooleanArray selectedItems = new SparseBooleanArray(); //item클릭 저장 객체
    private int prePosition = -1;    // 직전에 클릭됐던 Item의 position
    private int itemposition;

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
        itemposition = position;
        holder.onBind(list.get(position), position);
        System.out.println("position = " + position);



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
        private TextView choice_title1,choice_title2,test1,show;
        private ImageButton checkBtn;
        private WrongAnswersItemData wrongAnswersItemData;
        private int position;

        private Holder(View view){
            super(view);
            title = (TextView) view.findViewById(R.id.tv_title_wronganswers);
            content = (TextView) view.findViewById(R.id.tv_content);
            choice_title1 = (TextView)view.findViewById(R.id.tv_choice_title1);
            choice_title2 = (TextView)view.findViewById(R.id.tv_choice_title2);
            test1 = (TextView)view.findViewById(R.id.tv_hideTest1);
            show = (TextView)view.findViewById(R.id.tv_show);
            checkBtn = (ImageButton)view.findViewById(R.id.btn_wrongAnswerCheck);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
            public void onClick(View view) {
                    if (selectedItems.get(position)) {
                        // 펼쳐진 Item을 클릭 시
                        selectedItems.delete(position);
                    } else {
                        // 직전의 클릭됐던 Item의 클릭상태를 지움
                        selectedItems.delete(prePosition);
                        // 클릭한 Item의 position을 저장
                        selectedItems.put(position, true);
                    }
                    if (prePosition != -1) notifyItemChanged(prePosition);
                    notifyItemChanged(position);
                    prePosition = position;
                    System.out.println("itemposition = " + position);
                }
            });
        }
        /**
         * 클릭된 Item의 상태 변경
         * @param isExpanded Item을 펼칠 것인지 여부
         */
        private void changeVisibility(final boolean isExpanded) {
            // height 값을 dp로 지정해서 넣고싶으면 아래 소스를 이용
            int dpValue = 150;
            float d = context.getResources().getDisplayMetrics().density;
            int height = (int) (dpValue * d);
            // ValueAnimator.ofInt(int... values)는 View가 변할 값을 지정, 인자는 int 배열
            ValueAnimator va = isExpanded ? ValueAnimator.ofInt(0, height) : ValueAnimator.ofInt(height, 0);
            // Animation이 실행되는 시간, n/1000초
            va.setDuration(600);
            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    // value는 height 값
                    int value = (int) animation.getAnimatedValue();
                    // 해설 보여주기
                    test1.getLayoutParams().height = value;
                    test1.requestLayout();
                    test1.setVisibility(isExpanded ? View.VISIBLE  : View.GONE);
                    //체크 버튼
                    checkBtn.requestLayout();
                    checkBtn.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                    if(isExpanded ==  true){
                        show.setText("정답 및 해설 접기");
                    }
                    else{
                        show.setText("정답 및 해설 보기");
                    }
                }
            });
            // Animation start
            va.start();


        }

        public void onBind(WrongAnswersItemData wrongAnswersItemData, int position) {
            this.wrongAnswersItemData = wrongAnswersItemData;
            this.position = position;

            title.setText(list.get(itemposition).getQuestion().getTitle());
            content.setText(list.get(itemposition).getQuestion().getContent());
            choice_title1.setText(list.get(itemposition).getChoice().getContent());
            choice_title2.setText(list.get(itemposition).getChoice().getContent());
            test1.setText(list.get(itemposition).getQuestion().getComment());

            changeVisibility(selectedItems.get(position));
        }
    }

    }



