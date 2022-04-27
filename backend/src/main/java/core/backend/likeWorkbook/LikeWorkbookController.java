package core.backend.likeWorkbook;

import core.backend.global.dto.DataResponse;
import core.backend.likeWorkbook.domain.LikeWorkbook;
import core.backend.likeWorkbook.dto.LikeWorkbookSaveRequestDto;
import core.backend.likeWorkbook.service.LikeWorkbookService;
import core.backend.member.domain.Member;
import core.backend.workbook.dto.WorkbookResponseDto;
import core.backend.workbook.service.WorkbookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class LikeWorkbookController {

    private final LikeWorkbookService likeWorkbookService;
    private final WorkbookService workbookService;

    @GetMapping("/like-workbooks")
    public ResponseEntity<DataResponse> findAllWithAuth(
            @AuthenticationPrincipal Member member,
            @PageableDefault Pageable pageable) {
        List<WorkbookResponseDto> result = getWorkBookIdList(member.getId(), pageable)
                .stream()
                .map(id -> new WorkbookResponseDto(workbookService.findByIdOrThrow(id)))
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                DataResponse.builder().data(result).count(result.size()).build());
    }

    private List<Long> getWorkBookIdList(Long memberId, Pageable pageable) {
        return likeWorkbookService.findByMemberId(memberId, pageable)
                .stream()
                .map(LikeWorkbook::getWorkbookId)
                .collect(Collectors.toList());
    }

    @PostMapping("/like-workbook")
    public ResponseEntity<WorkbookResponseDto> saveV1(
            @AuthenticationPrincipal Member member,
            @RequestBody LikeWorkbookSaveRequestDto dto) {
        LikeWorkbook likeWorkbook = likeWorkbookService.save(dto.toEntity(member.getId()));
        return ResponseEntity.status(201)
                .body(new WorkbookResponseDto(
                        workbookService.findByIdOrThrow(likeWorkbook.getWorkbookId())));
    }
}
