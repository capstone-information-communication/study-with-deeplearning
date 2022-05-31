package core.backend.likeWorkbook.service;

import core.backend.likeWorkbook.domain.LikeWorkbook;
import core.backend.likeWorkbook.dto.LikeWorkbookSaveRequestDto;
import core.backend.likeWorkbook.exception.ExistLikeWorkbookException;
import core.backend.likeWorkbook.exception.LikeWorkbookNotRegisterException;
import core.backend.problem.workbook.domain.Workbook;
import core.backend.problem.workbook.dto.WorkbookResponseDto;
import core.backend.problem.workbook.service.WorkbookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikeWorkbookFacadeImpl implements LikeWorkbookFacade{

    private final WorkbookService workbookService;
    private final LikeWorkbookService likeWorkbookService;

    @Override
    public List<WorkbookResponseDto> findMyLikeWorkbook(Long memberId, Pageable pageable) {
        return getWorkBookIdList(memberId, pageable)
                .stream()
                .map(id -> new WorkbookResponseDto(workbookService.findByIdOrThrow(id)))
                .collect(Collectors.toList());
    }

    private List<Long> getWorkBookIdList(Long memberId, Pageable pageable) {
        return likeWorkbookService.findByMemberId(memberId, pageable)
                .stream()
                .map(LikeWorkbook::getWorkbookId)
                .collect(Collectors.toList());
    }

    @Override
    public Workbook saveAndGetWorkbook(Long memberId, LikeWorkbookSaveRequestDto dto) {
        isExistWorkbookId(
                dto.getWorkbook_id(),
                findByMemberIdAndGetLikeWorkbookId(memberId));
        workbookService.updateLikeCount(dto.getWorkbook_id());
        likeWorkbookService.save(dto.toEntity(memberId));
        return workbookService.findByIdOrThrow(dto.getWorkbook_id());
    }

    private void isExistWorkbookId(Long workbookId, List<Long> result) {
        if (result.contains(workbookId)) {
            throw new ExistLikeWorkbookException();
        }
    }

    private List<Long> findByMemberIdAndGetLikeWorkbookId(Long memberId) {
        return likeWorkbookService.findByMemberId(memberId)
                .stream()
                .map(LikeWorkbook::getWorkbookId)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteOrThrow(Long id, Long memberId) {
        LikeWorkbook likeWorkbook = likeWorkbookService.findByIdOrThrow(id);
        isRegisterMember(memberId, likeWorkbook.getMemberId());
        likeWorkbookService.deleteById(id);
    }

    private void isRegisterMember(Long memberId, Long registerId) {
        if (!Objects.equals(memberId, registerId)) {
            throw new LikeWorkbookNotRegisterException();
        }
    }
}
