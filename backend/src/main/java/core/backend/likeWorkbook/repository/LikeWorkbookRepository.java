package core.backend.likeWorkbook.repository;

import core.backend.likeWorkbook.domain.LikeWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeWorkbookRepository extends JpaRepository<LikeWorkbook, Long> {
    Page<LikeWorkbook> findByMemberId(Long memberId, Pageable pageable);

    List<LikeWorkbook> findByMemberId(Long memberId);
}
