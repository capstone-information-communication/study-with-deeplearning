package core.backend.likeWorkbook.dto;

import core.backend.likeWorkbook.domain.LikeWorkbook;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeWorkbookSaveRequestDto {
    private Long workbook_id;

    public LikeWorkbookSaveRequestDto(Long workbook_id) {
        this.workbook_id = workbook_id;
    }

    public LikeWorkbook toEntity(Long memberId) {
        return LikeWorkbook.builder()
                .workbookId(workbook_id)
                .memberId(memberId)
                .build();
    }
}
