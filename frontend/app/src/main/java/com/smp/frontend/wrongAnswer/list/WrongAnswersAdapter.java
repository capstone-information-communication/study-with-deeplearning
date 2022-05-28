package com.smp.frontend.wrongAnswer.list;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.smp.frontend.global.PreferencesManager;
import com.smp.frontend.R;
import com.smp.frontend.global.gsonParsing;
import com.smp.frontend.wrongAnswer.RetrofitClientWrongAnswer;
import com.smp.frontend.wrongAnswer.WrongAnswerController;
import com.smp.frontend.wrongAnswer.dto.DeleteWrongAnswerRequestDto;
import com.smp.frontend.wrongAnswer.dto.DeleteWrongAnswerResponseDto;
import com.smp.frontend.wrongAnswer.dto.WrongAnswerTestResponse;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WrongAnswersAdapter extends RecyclerView.Adapter<WrongAnswersAdapter.Holder> {

    private Context context;
    private List<WrongAnswersItemData> list = new ArrayList<>();
    private SparseBooleanArray selectedItems = new SparseBooleanArray(); //item클릭 저장 객체
    private int prePosition = -1;    // 직전에 클릭됐던 Item의 position
    private int itemposition;
    RetrofitClientWrongAnswer retrofitClientWrongAnswer = RetrofitClientWrongAnswer.getInstance();
    WrongAnswerController WrongAnswerController = RetrofitClientWrongAnswer.getRetrofitInterface();
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
        holder.onBind(list, position);

    }

    // 몇개의 데이터를 리스트로 뿌려줘야하는지 반드시 정의해줘야한다
    @Override
    public int getItemCount() {
        return list.size(); // RecyclerView의 size return
    }

    // ViewHolder는 하나의 View를 보존하는 역할을 한다
    public class Holder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView content;
        private TextView choice_title1, choice_title2,choice_title3,choice_title4, commentary, show;
        private Button checkBtn;
        private List<WrongAnswersItemData> list = new ArrayList<>();
        private int position;
        private RetrofitClientWrongAnswer retrofitClientWrongAnswer;
        private WrongAnswerController wrongAnswerController;
        long qeustionId, workbookId, wrongAnswerId;

        private Holder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tv_title_wronganswers);
            content = (TextView) view.findViewById(R.id.tv_content);
            choice_title1 = (TextView) view.findViewById(R.id.tv_choice_title1);
            choice_title2 = (TextView) view.findViewById(R.id.tv_choice_title2);
            choice_title3 = (TextView) view.findViewById(R.id.tv_choice_title3);
            choice_title4 = (TextView) view.findViewById(R.id.tv_choice_title4);
            commentary = (TextView) view.findViewById(R.id.tv_hideTest1);
            show = (TextView) view.findViewById(R.id.tv_show);
            checkBtn = (Button) view.findViewById(R.id.btn_wrongAnswerCheck);

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
                    System.out.println("prePosition = " + prePosition);
                    System.out.println("position = " + position);

                }
            });
        }

        /**
         * 클릭된 Item의 상태 변경
         *
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
                    commentary.getLayoutParams().height = value;
                    commentary.requestLayout();
                    commentary.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                    //체크 버튼
                    checkBtn.requestLayout();
                    checkBtn.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                    if (isExpanded == true) {
                        show.setText("정답 및 해설 접기");
                    } else {
                        show.setText("정답 및 해설 보기");
                    }
                }
            });
            // Animation start
            va.start();
        }
        public void onBind(List<WrongAnswersItemData> list, int position) {
            this.list = list;
            this.position = position;
            workbookId  = list.get(itemposition).getWorkbookId();

            String questionTitle = (list.get(itemposition).getQuestion().getTitle());
            String qeustionContent = (list.get(itemposition).getQuestion().getContent());
            String category = list.get(itemposition).getQuestion().getCategory();
            qeustionId  = (list.get(itemposition).getQuestion().getQuestionId());
            wrongAnswerId = (list.get(itemposition).getQuestion().getWrongAnswerId());

            title.setText(questionTitle);
            content.setText(qeustionContent);
            List<?> choiceList = list.get(itemposition).getChoice();
            List<String> choiceListComment = new ArrayList<>();
            int size = choiceList.size();
            gsonParsing instance = gsonParsing.getInstance();
            for(int i=0; i<size; i++){
                try {
                    WrongAnswerTestResponse wrongChoiceParsing = (WrongAnswerTestResponse)instance.parsing(
                            instance.ArrToString(instance.toJsonArr(choiceList),i),
                            WrongAnswerTestResponse.class
                    );
                    choiceListComment.add(wrongChoiceParsing.getContent());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            if(size <=0 ) {
                choice_title1.setVisibility(View.GONE);
                choice_title2.setVisibility(View.GONE);
                choice_title3.setVisibility(View.GONE);
                choice_title4.setVisibility(View.GONE);
                return;
            }
            else{
                try {
                    if(size <=0 ) {
                        return;
                    }
                    if (category.equals("BLANK") || category.equals("SHORT")) {
                        choice_title2.setVisibility(View.GONE);
                        choice_title3.setVisibility(View.GONE);
                        choice_title4.setVisibility(View.GONE);
                        choice_title1.setText(choiceListComment.get(0));
                        System.out.println("choiceList = " + choiceListComment.get(0));
                    } else {
                        choice_title1.setText(choiceListComment.get(0));
                        choice_title2.setText(choiceListComment.get(1));
                        choice_title3.setText(choiceListComment.get(2));
                        choice_title4.setText(choiceListComment.get(3));
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            commentary.setText(list.get(itemposition).getQuestion().getComment());
            changeVisibility(selectedItems.get(itemposition));

            checkBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DeleteWrongAnswerRequestDto requestDto = new DeleteWrongAnswerRequestDto(qeustionId,workbookId);
                    String token =PreferencesManager.getString(view.getContext(),"token");
                    Call<DeleteWrongAnswerResponseDto> request = WrongAnswerController.DeletWrongAnswer(token,requestDto,wrongAnswerId);
                    request.enqueue(new Callback<DeleteWrongAnswerResponseDto>() {
                        @Override
                        public void onResponse(Call<DeleteWrongAnswerResponseDto> call, Response<DeleteWrongAnswerResponseDto> response) {
                            if(response.code() == 200){
                                list.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, list.size());
                                System.out.println("response = " + response.body().getMessage());
                            }
                        }

                        @Override
                        public void onFailure(Call<DeleteWrongAnswerResponseDto> call, Throwable t) {
                            System.out.println("서버에러 ~~");
                            t.printStackTrace();
                        }
                    });
                   
                }
            });
        }
    }

}



