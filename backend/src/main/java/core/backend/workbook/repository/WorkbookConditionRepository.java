package core.backend.workbook.repository;

import core.backend.workbook.dto.WorkbookCondition;
import core.backend.workbook.domain.Workbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WorkbookConditionRepository {
    List<Workbook> findByIdOrThrow(Long id);

    List<Workbook> findByTitleOrThrow(String title);

    List<Workbook> findByDescriptionOrThrow(String description);

    Page<Workbook> searchOrThrow(WorkbookCondition condition, Pageable pageable);
}
