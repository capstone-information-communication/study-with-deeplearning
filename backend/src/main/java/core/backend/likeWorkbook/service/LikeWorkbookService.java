package core.backend.likeWorkbook.service;

import core.backend.likeWorkbook.domain.LikeWorkbook;
import core.backend.likeWorkbook.exception.LikeWorkbookNotFoundException;
import core.backend.likeWorkbook.repository.LikeWorkbookRepository;
import core.backend.workbook.repository.WorkbookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeWorkbookService {

    private final LikeWorkbookRepository likeWorkbookRepository;
    private final WorkbookRepository workbookRepository;

    @Transactional
    public Long save(LikeWorkbook likeWorkbook) {
        likeWorkbookRepository.save(likeWorkbook);
        return likeWorkbook.getId();
    }

    @Transactional
    public void deleteById(Long id) {
        likeWorkbookRepository.deleteById(id);
    }

    public Page<LikeWorkbook> findByMemberId(Long memberId, Pageable pageable) {
        return likeWorkbookRepository.findByMemberId(memberId, pageable);
    }

    public LikeWorkbook findByIdOrThrow(Long id) {
        return likeWorkbookRepository.findById(id)
                .orElseThrow(LikeWorkbookNotFoundException::new);
    }
}
