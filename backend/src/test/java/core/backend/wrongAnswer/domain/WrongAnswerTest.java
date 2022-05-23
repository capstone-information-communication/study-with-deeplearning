package core.backend.wrongAnswer.domain;

import core.backend.wrongProblem.wrongAnswer.domain.WrongAnswer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WrongAnswerTest {

    @Test
    @DisplayName("[WrongAnswer, 생성] 오답")
    void createWrongAnswer() {
        //given
        WrongAnswer wrongAnswer = WrongAnswer.builder()
                .memberId(1L)
                .workbookId(2L)
                .questionId(3L)
                .build();

        //when
        //then
        assertThat(wrongAnswer).isInstanceOf(WrongAnswer.class);
        assertThat(wrongAnswer.getMemberId()).isEqualTo(1L);
        assertThat(wrongAnswer.getWorkbookId()).isEqualTo(2L);
        assertThat(wrongAnswer.getQuestionId()).isEqualTo(3L);
    }
}