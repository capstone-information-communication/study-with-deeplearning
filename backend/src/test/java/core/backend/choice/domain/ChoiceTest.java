package core.backend.choice.domain;

import core.backend.commentary.domain.Commentary;
import core.backend.question.domain.Category;
import core.backend.question.domain.Question;
import core.backend.workbook.domain.Workbook;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ChoiceTest {

    @Test
    @DisplayName("[Choice, 생성] 질문")
    void createChoice() {
        //given
        Workbook workbook = Workbook.builder()
                .memberId(1L)
                .title("협력적 의사소통 2주차")
                .description("협력적 의사소통 중간고사 대비")
                .build();

        Commentary commentary = Commentary.builder()
                .content("문제에 대한 해설 저장")
                .build();

        Question question = Question.builder()
                .title("다음 보기 중 알맞은 것을 고르세요.")
                .content("보기로 주어질 상황이나 대화가 들어갈 자리")
                .category(Category.SHORT)
                .commentary(commentary)
                .workbook(workbook)
                .build();

        //when
        Choice choice = Choice.builder()
                .state(State.ANSWER)
                .content("1")
                .question(question)
                .build();

        //then
        assertThat(choice).isInstanceOf(Choice.class);
        assertThat(choice.getContent()).isEqualTo("1");
        assertThat(choice.getState()).isEqualTo(State.ANSWER);

        assertThat(choice.getQuestion()).isInstanceOf(Question.class);
        assertThat(choice.getQuestion().getTitle()).isEqualTo(question.getTitle());
    }
}