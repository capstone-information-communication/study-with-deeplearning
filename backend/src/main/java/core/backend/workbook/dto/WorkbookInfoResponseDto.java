package core.backend.workbook.dto;

import core.backend.workbook.domain.Workbook;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class WorkbookInfoResponseDto {
    private Long id;
    private String title;
    private String description;
    private Long likeCount;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;

    public WorkbookInfoResponseDto(Workbook entity) {
        id = entity.getId();
        title = entity.getTitle();
        description = entity.getDescription();
        likeCount = entity.getLikeCount();

        updatedAt = entity.getUpdatedAt();
        createdAt = entity.getCreatedAt();
    }
}
