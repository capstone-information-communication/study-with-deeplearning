package core.backend.workbook.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WorkbookTest {

    @Test
    @DisplayName("[Workbook, 생성] 문제집")
    void createWorkbook() {
        //given
        Workbook workbook = Workbook.builder()
                .memberId(1L)
                .title("운영체제 5주차")
                .description("운영체제 중간고사 대비")
                .build();

        //when
        //then
        assertThat(workbook).isInstanceOf(Workbook.class);
        assertThat(workbook.getMemberId()).isEqualTo(1L);
        assertThat(workbook.getTitle()).isEqualTo("운영체제 5주차");
        assertThat(workbook.getDescription()).isEqualTo("운영체제 중간고사 대비");
        assertThat(workbook.getLikeCount()).isEqualTo(0L);
    }
}