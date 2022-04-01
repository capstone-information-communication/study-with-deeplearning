package core.backend.problem.dto;

import core.backend.problem.domain.Problem;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProblemResponseDto {
    private Long id;
    private String question;
    private String answer;

    public ProblemResponseDto(Problem entity) {
        id = entity.getId();
        question = entity.getQuestion();
        answer = entity.getAnswer();
    }
}

