package core.backend.workbook.repository;

import core.backend.workbook.entity.Workbook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkbookRepository extends JpaRepository<Workbook, Long> {
}