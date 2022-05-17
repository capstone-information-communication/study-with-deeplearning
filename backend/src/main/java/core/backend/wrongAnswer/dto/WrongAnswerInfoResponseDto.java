package core.backend.wrongAnswer.dto;

import core.backend.workbook.domain.Workbook;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class WrongAnswerInfoResponseDto {
    private Long id;
    private String title;
    private String description;
    private Long likeCount;
    private List<WrongAnswerQuestionResponseDto> wrongAnswerQuestionList;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;

    public WrongAnswerInfoResponseDto(Workbook entity, List<WrongAnswerItem> wrongAnswerItemList) {
        id = entity.getId();
        title = entity.getTitle();
        description = entity.getDescription();
        likeCount = entity.getLikeCount();

        wrongAnswerQuestionList = wrongAnswerItemList.stream()
                .map(item -> new WrongAnswerQuestionResponseDto(item.getQuestion(), item.getWrongAnswerId()))
                .collect(Collectors.toList());

        updatedAt = entity.getUpdatedAt();
        createdAt = entity.getCreatedAt();
    }
}
