package core.backend.wrongProblem.wrongQuestion.service;

import core.backend.wrongProblem.wrongQuestion.domain.WrongQuestion;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WrongQuestionService {
    Long save(WrongQuestion wrongQuestion);

    void correctAnswer(WrongQuestion wrongQuestion);

    WrongQuestion findByIdOrThrow(Long id);

    List<WrongQuestion> findAll(Pageable pageable);

    void deleteByIdOrThrow(Long id, Long memberId);
}
