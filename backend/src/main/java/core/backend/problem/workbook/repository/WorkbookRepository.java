package core.backend.problem.workbook.repository;

import core.backend.problem.workbook.domain.Workbook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkbookRepository extends JpaRepository<Workbook, Long> {
    Optional<Workbook> findByTitle(String title);
}
