package core.backend.wrongProblem.wrongAnswer.repository;

import core.backend.wrongProblem.wrongAnswer.domain.WrongAnswer;
import core.backend.wrongProblem.wrongAnswer.dto.WrongAnswerCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WrongAnswerConditionRepository {
    Page<WrongAnswer> searchOrThrow(WrongAnswerCondition condition, Pageable pageable);
}
