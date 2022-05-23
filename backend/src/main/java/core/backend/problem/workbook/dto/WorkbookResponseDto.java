package core.backend.problem.workbook.dto;

import core.backend.problem.question.dto.QuestionResponseDto;
import core.backend.problem.workbook.domain.Workbook;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class WorkbookResponseDto {
    private Long id;
    private String title;
    private String description;
    private Long likeCount;
    private Integer questionCount;

    private List<QuestionResponseDto> questionList;

    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;

    @Builder
    public WorkbookResponseDto(Workbook entity) {
        id = entity.getId();
        title = entity.getTitle();
        description = entity.getDescription();
        likeCount = entity.getLikeCount();
        questionCount = entity.getQuestionList().size();

        questionList = entity.getQuestionList()
                .stream()
                .map(QuestionResponseDto::new)
                .collect(Collectors.toList());

        updatedAt = entity.getUpdatedAt();
        createdAt = entity.getCreatedAt();
    }
}
