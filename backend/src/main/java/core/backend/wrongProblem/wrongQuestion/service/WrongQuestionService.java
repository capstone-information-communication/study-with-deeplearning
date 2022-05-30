package core.backend.wrongProblem.wrongQuestion.service;

import core.backend.wrongProblem.wrongQuestion.domain.WrongQuestion;
import core.backend.wrongProblem.wrongQuestion.exception.WrongQuestionNotFoundException;
import core.backend.wrongProblem.wrongQuestion.repository.WrongQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WrongQuestionService {

    private final WrongQuestionRepository wrongQuestionRepository;

    @Transactional
    public Long save(WrongQuestion wrongQuestion) {
        wrongQuestionRepository.save(wrongQuestion);
        return wrongQuestion.getId();
    }

    @Transactional
    public void correctAnswer(WrongQuestion wrongQuestion) {
        wrongQuestion.answered();
    }

    public WrongQuestion findByIdOrThrow(Long id) {
        return wrongQuestionRepository.findById(id)
                .orElseThrow(WrongQuestionNotFoundException::new);
    }

    public List<WrongQuestion> findAll(Pageable pageable) {
        return wrongQuestionRepository.findAll(pageable).toList();
    }
}
