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

    @Builder
    public WorkbookCategoryResponseDto(Workbook entity) {
        id = entity.getId();
        title = entity.getTitle();
        description = entity.getDescription();
        likeCount = entity.getLikeCount();

        questionList = getCategoricalMap(new HashMap<Category, List<Question>>(), entity.getQuestionList())
                .entrySet().stream()
                .map(item -> new QuestionCategoryResponseDto(item.getKey(), item.getValue()))
                .collect(Collectors.toList());

        updatedAt = entity.getUpdatedAt();
        createdAt = entity.getCreatedAt();
    }

    private HashMap<Category, List<Question>> getCategoricalMap(HashMap<Category, List<Question>> map, List<Question> questionList) {
        initMap(map);
        for (Question question : questionList) {
            map.get(question.getCategory()).add(question);
        }
        return map;
    }

    private void initMap(HashMap map) {
        for (Category value : Category.values()) {
            map.put(value, new ArrayList<>());
        }
    }
}
