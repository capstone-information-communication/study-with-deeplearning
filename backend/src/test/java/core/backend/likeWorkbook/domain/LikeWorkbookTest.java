package core.backend.likeWorkbook.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LikeWorkbookTest {

    @Test
    @DisplayName("[LikeWorkbook, 생성] 좋아요 문제집")
    void createLikeWorkbook() {
        //given
        LikeWorkbook likeWorkbook = LikeWorkbook.builder()
                .memberId(1L)
                .workbookId(2L)
                .build();

        //when
        //then
        assertThat(likeWorkbook).isInstanceOf(LikeWorkbook.class);
        assertThat(likeWorkbook.getMemberId()).isEqualTo(1L);
        assertThat(likeWorkbook.getWorkbookId()).isEqualTo(2L);
    }
}