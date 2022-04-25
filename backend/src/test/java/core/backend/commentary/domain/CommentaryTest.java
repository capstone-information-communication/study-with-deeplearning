package core.backend.commentary.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CommentaryTest {

    @Test
    @DisplayName("[Commentary, 생성] 해설")
    void createCommentary() {
        //given
        Commentary commentary = Commentary.builder()
                .content("해설 저장")
                .build();

        //when
        //then
        assertThat(commentary).isInstanceOf(Commentary.class);
        assertThat(commentary.getContent()).isEqualTo("해설 저장");
    }
}