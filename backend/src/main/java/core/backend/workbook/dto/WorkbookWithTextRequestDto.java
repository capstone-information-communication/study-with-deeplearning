package core.backend.workbook.dto;

import core.backend.workbook.domain.Workbook;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkbookWithTextRequestDto {
    private String title;
    private String description;
    private String text;

    public WorkbookWithTextRequestDto(String title, String description, String text) {
        this.title = title;
        this.description = description;
        this.text = text;
    }

    public Workbook toEntity(Long memberId) {
        return Workbook.builder()
                .title(title)
                .description(description)
                .memberId(memberId)
                .build();
    }
}
