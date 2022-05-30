package core.backend.wrongProblem.wrongWorkbook.service;

import core.backend.wrongProblem.wrongWorkbook.domain.WrongWorkbook;
import core.backend.wrongProblem.wrongWorkbook.exception.WrongWorkbookExistTitleException;
import core.backend.wrongProblem.wrongWorkbook.exception.WrongWorkbookNotFoundException;
import core.backend.wrongProblem.wrongWorkbook.exception.WrongWorkbookNotYoursException;
import core.backend.wrongProblem.wrongWorkbook.repository.WrongWorkbookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WrongWorkbookServiceImpl implements WrongWorkbookService {
    private final WrongWorkbookRepository wrongWorkbookRepository;

    @Override
    @Transactional
    public Long save(WrongWorkbook wrongWorkbook) {
        isUniqueWrongWorkbook(wrongWorkbook);
        wrongWorkbookRepository.save(wrongWorkbook);
        return wrongWorkbook.getId();
    }

    private void isUniqueWrongWorkbook(WrongWorkbook wrongWorkbook) {
        wrongWorkbookRepository.findByMemberId(wrongWorkbook.getMemberId())
                .stream()
                .filter(item -> item.getTitle().equals(wrongWorkbook.getTitle()))
                .findFirst()
                .orElseThrow(WrongWorkbookExistTitleException::new);
    }

    @Override
    @Transactional
    public void deleteByIdOrThrow(Long id, Long memberId) {
        isMemberWrongWorkbookOrThrow(id, memberId);
        findByIdOrThrow(id);
        wrongWorkbookRepository.deleteById(id);
    }

    private void isMemberWrongWorkbookOrThrow(Long id, Long memberId) {
        findAllByMemberId(memberId)
                .stream()
                .filter(item -> item.getId().equals(id))
                .findAny()
                .orElseThrow(WrongWorkbookNotYoursException::new);
    }

    @Override
    public WrongWorkbook findByIdOrThrow(Long id) {
        return wrongWorkbookRepository.findById(id)
                .orElseThrow(WrongWorkbookNotFoundException::new);
    }

    @Override
    public List<WrongWorkbook> findAll(Pageable pageable) {
        return wrongWorkbookRepository.findAll(pageable).toList();
    }

    @Override
    public List<WrongWorkbook> findAllByMemberId(Long memberId) {
        return wrongWorkbookRepository.findByMemberId(memberId).toList();
    }
}
