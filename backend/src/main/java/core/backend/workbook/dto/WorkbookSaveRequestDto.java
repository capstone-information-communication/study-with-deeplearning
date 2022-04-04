package core.backend.workbook.dto;

import core.backend.workbook.domain.Workbook;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkbookSaveRequestDto {
    private String title;
    private String description;

    public WorkbookSaveRequestDto(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Workbook toEntity(Long memberId) {
        return Workbook.builder()
                .title(title)
                .description(description)
                .memberId(memberId)
                .build();
    }
}
