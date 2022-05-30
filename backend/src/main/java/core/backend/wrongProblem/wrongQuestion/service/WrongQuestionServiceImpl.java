package core.backend.wrongProblem.wrongQuestion.service;

import core.backend.member.domain.WrongFigure;
import core.backend.member.service.MemberService;
import core.backend.wrongProblem.wrongQuestion.domain.WrongQuestion;
import core.backend.wrongProblem.wrongQuestion.exception.WrongQuestionNotFoundException;
import core.backend.wrongProblem.wrongQuestion.exception.WrongQuestionNotYoursException;
import core.backend.wrongProblem.wrongQuestion.repository.WrongQuestionRepository;
import core.backend.wrongProblem.wrongWorkbook.domain.WrongWorkbook;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WrongQuestionServiceImpl implements WrongQuestionService {

    private final WrongQuestionRepository wrongQuestionRepository;
    private final MemberService memberService;

    @Override
    @Transactional
    public Long save(Long id, WrongQuestion wrongQuestion) {
        WrongFigure wrongFigure = memberService.findByIdOrThrow(id)
                .getWrongFigure();
        wrongFigure.addByCategory(wrongQuestion.getCategory(), 1);
        System.out.println("wrongFigure.toString() = " + wrongFigure.toString());
        wrongQuestionRepository.save(wrongQuestion);
        return wrongQuestion.getId();
    }

    @Override
    @Transactional
    public void correctAnswer(WrongQuestion wrongQuestion) {
        wrongQuestion.answered();
    }

    @Override
    public WrongQuestion findByIdOrThrow(Long id) {
        return wrongQuestionRepository.findById(id)
                .orElseThrow(WrongQuestionNotFoundException::new);
    }

    @Override
    public List<WrongQuestion> findAll(Pageable pageable) {
        return wrongQuestionRepository.findAll(pageable).toList();
    }

    @Override
    public void deleteByIdOrThrow(Long id, Long memberId) {
        WrongWorkbook wrongWorkbook = findByIdOrThrow(id)
                .getWrongWorkbook();
        if (wrongWorkbook.getMemberId().equals(memberId)) {
            wrongQuestionRepository.deleteById(id);
        } else {
            throw new WrongQuestionNotYoursException();
        }
    }
}
