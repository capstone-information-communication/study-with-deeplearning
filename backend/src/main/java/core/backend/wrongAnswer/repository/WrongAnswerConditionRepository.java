package core.backend.wrongAnswer.repository;

import core.backend.wrongAnswer.domain.WrongAnswer;
import core.backend.wrongAnswer.dto.WrongAnswerCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WrongAnswerConditionRepository {
    Page<WrongAnswer> searchOrThrow(WrongAnswerCondition condition, Pageable pageable);
}
