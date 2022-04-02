package core.backend.likeWorkbook.repository;

import core.backend.likeWorkbook.domain.LikeWorkbook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeWorkbookRepository extends JpaRepository<LikeWorkbook, Long> {
}
