package core.backend.problem.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProblemUpdateRequestDto {
    private Long id;
    private String question;
    private String answer;

    public ProblemUpdateRequestDto(Long id, String question, String answer) {
        this.id = id;
        this.question = question;
        this.answer = answer;
    }
}
