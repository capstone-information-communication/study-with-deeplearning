package core.backend.wrongProblem.wrongWorkbook.service;

import core.backend.wrongProblem.wrongWorkbook.domain.WrongWorkbook;
import core.backend.wrongProblem.wrongWorkbook.exception.WrongWorkbookExistTitleException;
import core.backend.wrongProblem.wrongWorkbook.exception.WrongWorkbookNotFoundException;
import core.backend.wrongProblem.wrongWorkbook.repository.WrongWorkbookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WrongWorkbookService {
    private final WrongWorkbookRepository wrongWorkbookRepository;

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

    public WrongWorkbook findByIdOrThrow(Long id) {
        return wrongWorkbookRepository.findById(id)
                .orElseThrow(WrongWorkbookNotFoundException::new);
    }

    public List<WrongWorkbook> findAll(Pageable pageable) {
        return wrongWorkbookRepository.findAll(pageable).toList();
    }
}
