package core.backend.workbook.dto;

import core.backend.question.domain.Question;
import core.backend.question.dto.QuestionResponseDto;
import core.backend.workbook.domain.Workbook;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class WorkbookInfoResponseDto {
    private Long id;
    private String title;
    private String description;
    private Long likeCount;
    private List<QuestionResponseDto> questionList;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;

    public WorkbookInfoResponseDto(Workbook entity, List<Question> questionList) {
        id = entity.getId();
        title = entity.getTitle();
        description = entity.getDescription();
        likeCount = entity.getLikeCount();

        this.questionList = questionList.stream()
                .map(QuestionResponseDto::new)
                .collect(Collectors.toList());

        updatedAt = entity.getUpdatedAt();
        createdAt = entity.getCreatedAt();
    }
}
