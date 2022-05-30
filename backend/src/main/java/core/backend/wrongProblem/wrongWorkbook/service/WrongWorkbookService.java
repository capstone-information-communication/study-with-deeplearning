package core.backend.wrongProblem.wrongWorkbook.service;

import core.backend.wrongProblem.wrongWorkbook.domain.WrongWorkbook;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WrongWorkbookService {
    Long save(WrongWorkbook wrongWorkbook);

    void deleteByIdOrThrow(Long id, Long memberId);

    WrongWorkbook findByIdOrThrow(Long id);

    List<WrongWorkbook> findAll(Pageable pageable);

    List<WrongWorkbook> findAllByMemberId(Long memberId);
}
