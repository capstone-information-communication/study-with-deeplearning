package core.backend.problem.workbook.dto;

import core.backend.problem.workbook.domain.Workbook;
import core.backend.problem.question.dto.QuestionCategoryResponseDto;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class WorkbookCategoryResponseDto {
    private Long id;
    private String title;
    private String description;
    private Long likeCount;

    private List<QuestionCategoryResponseDto> questionList;

    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;

    public WorkbookCategoryResponseDto(Workbook entity, List<QuestionCategoryResponseDto> questionCategoryResponseDtoList) {
        id = entity.getId();
        title = entity.getTitle();
        description = entity.getDescription();
        likeCount = entity.getLikeCount();

        questionList = questionCategoryResponseDtoList;

        updatedAt = entity.getUpdatedAt();
        createdAt = entity.getCreatedAt();
    }
}
