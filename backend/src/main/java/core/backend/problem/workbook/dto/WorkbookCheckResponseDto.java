package core.backend.problem.workbook.dto;

import lombok.Getter;

@Getter
public class WorkbookCheckResponseDto {
    private int answer;
    private int wrong;

    public WorkbookCheckResponseDto(int answer, int wrong) {
        this.answer = answer;
        this.wrong = wrong;
    }
}
