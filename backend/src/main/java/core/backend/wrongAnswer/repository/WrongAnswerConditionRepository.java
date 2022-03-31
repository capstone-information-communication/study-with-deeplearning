package core.backend.wrongAnswer.repository;

import core.backend.wrongAnswer.domain.WrongAnswer;
import core.backend.wrongAnswer.dto.WrongAnswerCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WrongAnswerConditionRepository {
    List<WrongAnswer> findByMemberIdOrThrow(Long memberId);

    List<WrongAnswer> findByProblemIdOrThrow(Long problemId);

    List<WrongAnswer> findByWorkbookIdOrThrow(Long workbookId);

    Page<WrongAnswer> searchOrThrow(WrongAnswerCondition condition, Pageable pageable);
}
