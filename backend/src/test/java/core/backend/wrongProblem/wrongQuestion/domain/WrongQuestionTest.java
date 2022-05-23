package core.backend.wrongProblem.wrongQuestion.domain;

import core.backend.problem.choice.domain.Choice;
import core.backend.problem.choice.domain.ChoiceData;
import core.backend.problem.choice.domain.State;
import core.backend.problem.question.domain.Category;
import core.backend.problem.question.domain.Commentary;
import core.backend.problem.question.domain.Question;
import core.backend.problem.question.domain.QuestionData;
import core.backend.problem.workbook.domain.Workbook;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class WrongQuestionTest {

    @Test
    @DisplayName("[WrongQuestion] 단일 생성")
    public void create() {
        //given
        Workbook workbook = Workbook.builder()
                .memberId(1L)
                .title("운영체제 5주차")
                .description("운영체제 중간고사 대비")
                .build();

        Commentary commentary = Commentary.builder()
                .comment("문제에 대한 해설 저장")
                .build();

        Question question = Question.builder()
                .title("다음 보기 중 알맞은 것을 고르세요.")
                .content("보기로 주어질 상황이나 대화가 들어갈 자리")
                .category(Category.SHORT)
                .commentary(commentary)
                .workbook(workbook)
                .build();

        List<Choice> choiceList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {

            if (i != 3) {
                choiceList.add(
                        Choice.builder()
                                .question(question)
                                .content("choice" + i)
                                .state(State.WRONG)
                                .build()
                );
            } else {
                choiceList.add(
                        Choice.builder()
                                .question(question)
                                .content("choice" + i)
                                .state(State.ANSWER)
                                .build()
                );
            }
        }

        //when
        List<ChoiceData> result = choiceList.stream()
                .map(ChoiceData::new)
                .collect(Collectors.toList());
        WrongQuestion wrongQuestion = WrongQuestion.builder()
                .question(new QuestionData(question))
                .choiceList(result)
                .build();

        //then
        assertThat(wrongQuestion.getTitle()).isEqualTo(question.getTitle());
        assertThat(wrongQuestion.getContent()).isEqualTo(question.getContent());
        assertThat(wrongQuestion.getCategory()).isEqualTo(question.getCategory());
        assertThat(wrongQuestion.getState()).isEqualTo(WrongQuestionState.WRONG);
        assertThat(wrongQuestion.getCommentary().getComment()).isEqualTo(question.getCommentary().getComment());
        for (int i = 0; i < choiceList.size(); i++) {
            assertThat(choiceList.get(i).getContent()).isEqualTo(wrongQuestion.getChoiceList().get(i).getContent());
            assertThat(choiceList.get(i).getState()).isEqualTo(wrongQuestion.getChoiceList().get(i).getState());
        }
    }
}