package core.backend.problem.workbook.service;

import core.backend.problem.workbook.domain.Workbook;
import core.backend.problem.workbook.dto.WorkbookCondition;
import core.backend.problem.workbook.dto.WorkbookUpdateRequestDto;
import core.backend.problem.workbook.repository.WorkbookConditionRepository;
import core.backend.problem.workbook.repository.WorkbookRepository;
import core.backend.problem.workbook.exception.WorkbookNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorkbookService {

    private final WorkbookRepository workbookRepository;
    private final WorkbookConditionRepository workbookConditionRepository;

    @Transactional
    public Long save(Workbook workbook) {
        workbookRepository.save(workbook);
        return workbook.getId();
    }

    @Transactional
    public Long update(Workbook workbook, WorkbookUpdateRequestDto dto) {
        return workbook
                .update(dto.getTitle(), dto.getDescription());
    }

    @Transactional
    public void updateLikeCount(Long workbookId) {
        Workbook workbook = findByIdOrThrow(workbookId);
        workbook.addLikeCount();
    }

    public Workbook findByIdOrThrow(Long id) {
        return workbookRepository.findById(id)
                .orElseThrow(WorkbookNotFoundException::new);
    }

    public Optional<Workbook> findByTitle(String title) {
        return workbookRepository.findByTitle(title);
    }

    public Page<Workbook> findAll(Pageable pageable) {
        return workbookRepository.findAll(pageable);
    }

    public Page<Workbook> findAllOrderedBy(String title, Pageable pageable) {
        return workbookConditionRepository.findAllWithOrderBy(title, pageable);
    }

    public Page<Workbook> search(WorkbookCondition condition, Pageable pageable) {
        return workbookConditionRepository.searchOrThrow(condition, pageable);
    }

    @Transactional
    public void deleteById(Long id) {
        workbookRepository.deleteById(id);
    }
}
