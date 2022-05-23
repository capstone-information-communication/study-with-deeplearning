package core.backend.problem.question.service;

import core.backend.problem.question.domain.Category;
import core.backend.problem.question.domain.Question;
import core.backend.problem.question.dto.QuestionUpdateRequestDto;
import core.backend.problem.question.exception.QuestionNotFoundException;
import core.backend.problem.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Transactional
    public Long save(Question question) {
        questionRepository.save(question);
        return question.getId();
    }

    @Transactional
    public Question update(Long id, QuestionUpdateRequestDto dto) {
        Question question = findByIdOrThrow(id);
        question.update(dto);
        return question;
    }

    @Transactional
    public void deleteById(Long id) {
        questionRepository.deleteById(id);
    }

    public Question findByIdOrThrow(Long id) {
        return questionRepository
                .findById(id)
                .orElseThrow(QuestionNotFoundException::new);
    }

    public Page<Question> findAll(Pageable pageable) {
        return questionRepository.findAll(pageable);
    }

    public Page<Question> findByCategory(Category category, Pageable pageable) {
        return questionRepository.findByCategory(category, pageable);
    }
}
