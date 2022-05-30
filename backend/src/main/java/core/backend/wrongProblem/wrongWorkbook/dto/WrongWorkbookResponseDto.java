package core.backend.wrongProblem.wrongWorkbook.dto;

import core.backend.wrongProblem.wrongQuestion.dto.WrongQuestionResponseDto;
import core.backend.wrongProblem.wrongWorkbook.domain.WrongWorkbook;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class WrongWorkbookResponseDto {
    private Long id;
    private Long memberId;
    private String title;
    private String description;

    private List<WrongQuestionResponseDto> wrongQuestionList;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public WrongWorkbookResponseDto(WrongWorkbook entity) {
        id = entity.getId();
        memberId = entity.getMemberId();
        title = entity.getTitle();
        description = entity.getDescription();

        wrongQuestionList = entity.getWrongQuestionList().stream()
                .map(WrongQuestionResponseDto::new)
                .collect(Collectors.toList());

        createdAt = entity.getCreatedAt();
        updatedAt = entity.getUpdatedAt();
    }
}
