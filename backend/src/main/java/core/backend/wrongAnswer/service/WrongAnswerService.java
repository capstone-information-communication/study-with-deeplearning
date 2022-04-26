package core.backend.wrongAnswer.service;

import core.backend.wrongAnswer.domain.WrongAnswer;
import core.backend.wrongAnswer.dto.WrongAnswerCondition;
import core.backend.wrongAnswer.exception.WrongAnswerNotFoundException;
import core.backend.wrongAnswer.repository.WrongAnswerConditionRepositoryImpl;
import core.backend.wrongAnswer.repository.WrongAnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AbstractPageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WrongAnswerService {

    private final WrongAnswerRepository wrongAnswerRepository;
    private final WrongAnswerConditionRepositoryImpl wrongAnswerConditionRepository;

    @Transactional
    public Long save(WrongAnswer wrongAnswer) {
        wrongAnswerRepository.save(wrongAnswer);
        return wrongAnswer.getId();
    }

    @Transactional
    public void deleteById(Long id) {
        wrongAnswerRepository.deleteById(id);
    }

    public Page<WrongAnswer> findByMember(Long memberId, Pageable pageable) {
        return wrongAnswerRepository.findByMemberId(memberId, pageable);
    }

    public Page<WrongAnswer> findByCondition(WrongAnswerCondition condition, Pageable pageable) {
        return wrongAnswerConditionRepository.searchOrThrow(condition, pageable);
    }

    public WrongAnswer findByIdOrThrow(Long id) {
        return wrongAnswerRepository.findById(id)
                .orElseThrow(WrongAnswerNotFoundException::new);
    }
}
