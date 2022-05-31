package core.backend.likeWorkbook.service;

import core.backend.likeWorkbook.dto.LikeWorkbookSaveRequestDto;
import core.backend.problem.workbook.domain.Workbook;
import core.backend.problem.workbook.dto.WorkbookResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LikeWorkbookFacade {
    List<WorkbookResponseDto> findMyLikeWorkbook(Long memberId, Pageable pageable);
    Workbook saveAndGetWorkbook(Long memberId, LikeWorkbookSaveRequestDto dto);
    void deleteOrThrow(Long id, Long memberId);
}
