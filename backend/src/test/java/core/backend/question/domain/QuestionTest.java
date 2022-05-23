package core.backend.question.domain;

import core.backend.problem.question.domain.Category;
import core.backend.problem.question.domain.Commentary;
import core.backend.problem.question.domain.Question;
import core.backend.problem.workbook.domain.Workbook;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class QuestionTest {

    @Test
    @DisplayName("[Question, 생성] 문제")
    void createQuestion() {
        //given
        Workbook workbook = Workbook.builder()
                .memberId(1L)
                .title("운영체제 5주차")
                .description("운영체제 중간고사 대비")
                .build();

        Commentary commentary = Commentary.builder()
                .comment("문제에 대한 해설 저장")
                .build();

        //when
        Question question = Question.builder()
                .title("다음 보기 중 알맞은 것을 고르세요.")
                .content("보기로 주어질 상황이나 대화가 들어갈 자리")
                .category(Category.SHORT)
                .commentary(commentary)
                .workbook(workbook)
                .build();

        //then
        assertThat(question).isInstanceOf(Question.class);
        assertThat(question.getTitle()).isEqualTo("다음 보기 중 알맞은 것을 고르세요.");
        assertThat(question.getContent()).isEqualTo("보기로 주어질 상황이나 대화가 들어갈 자리");
        assertThat(question.getCategory()).isEqualTo(Category.SHORT);

        assertThat(question.getCommentary().getComment()).isEqualTo(commentary.getComment());
        assertThat(question.getWorkbook().getTitle()).isEqualTo(workbook.getTitle());
    }

}