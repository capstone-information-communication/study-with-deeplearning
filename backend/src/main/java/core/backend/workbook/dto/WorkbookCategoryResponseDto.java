package core.backend.workbook.dto;

import core.backend.question.domain.Category;
import core.backend.question.domain.Question;
import core.backend.question.dto.QuestionCategoryResponseDto;
import core.backend.workbook.domain.Workbook;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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
