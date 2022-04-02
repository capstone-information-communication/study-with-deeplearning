package core.backend.workbook.repository;

import core.backend.workbook.dto.WorkbookCondition;
import core.backend.workbook.entity.Workbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface WorkbookConditionRepository {
    Optional<Workbook> findByTitle(String title);

    Page<Workbook> findAllWithOrderBy(String title, Pageable pageable);

    Page<Workbook> searchOrThrow(WorkbookCondition condition, Pageable pageable);
}
