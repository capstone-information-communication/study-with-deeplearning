package core.backend.workbook.dto;

import core.backend.problem.dto.ProblemResponseDto;
import core.backend.workbook.domain.Workbook;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class WorkbookResponseDto {
    private Long id;
    private String title;
    private String description;
    private List<ProblemResponseDto> problemList;

    @Builder
    public WorkbookResponseDto(Workbook entity) {
        id = entity.getId();
        title = entity.getTitle();
        description = entity.getDescription();
        problemList = entity.getProblemList()
                .stream()
                .map(ProblemResponseDto::new)
                .collect(Collectors.toList());
    }
}
