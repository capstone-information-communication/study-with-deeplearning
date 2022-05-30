package core.backend.wrongProblem.wrongWorkbook.repository;

import core.backend.wrongProblem.wrongWorkbook.domain.WrongWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WrongWorkbookRepository extends JpaRepository<WrongWorkbook, Long> {
    Page<WrongWorkbook> findByMemberId(Long memberId);
}
