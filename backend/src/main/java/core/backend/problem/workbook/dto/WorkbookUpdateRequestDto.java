package core.backend.problem.workbook.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkbookUpdateRequestDto {
    private String title;
    private String description;

    public WorkbookUpdateRequestDto(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
