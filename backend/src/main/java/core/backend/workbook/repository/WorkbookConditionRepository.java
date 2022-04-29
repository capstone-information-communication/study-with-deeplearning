package core.backend.workbook.repository;

import core.backend.workbook.domain.Workbook;
import core.backend.workbook.dto.WorkbookCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WorkbookConditionRepository {
    Page<Workbook> findAllWithOrderBy(String title, Pageable pageable);

    Page<Workbook> searchOrThrow(WorkbookCondition condition, Pageable pageable);
}
