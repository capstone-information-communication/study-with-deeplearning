package core.backend.likeWorkbook;

import core.backend.global.dto.DataResponse;
import core.backend.global.dto.DefaultDeleteResponseDto;
import core.backend.likeWorkbook.dto.LikeWorkbookSaveRequestDto;
import core.backend.likeWorkbook.service.LikeWorkbookFacade;
import core.backend.member.domain.Member;
import core.backend.problem.workbook.dto.WorkbookResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class LikeWorkbookController {

    private final LikeWorkbookFacade likeWorkbookFacade;

    @GetMapping("/like-workbooks")
    public ResponseEntity<DataResponse> findAllWithAuth(
            @AuthenticationPrincipal Member member,
            @PageableDefault Pageable pageable) {
        List<WorkbookResponseDto> result = likeWorkbookFacade.findMyLikeWorkbook(member.getId(), pageable);
        return ResponseEntity.ok(
                DataResponse.builder().data(result).count(result.size()).build());
    }

    @PostMapping("/like-workbook")
    public ResponseEntity<WorkbookResponseDto> saveV1(
            @AuthenticationPrincipal Member member,
            @RequestBody LikeWorkbookSaveRequestDto dto) {
        return ResponseEntity.status(201)
                .body(new WorkbookResponseDto(likeWorkbookFacade.saveAndGetWorkbook(member.getId(), dto)));
    }

    @DeleteMapping("/like-workbook/{id}")
    public ResponseEntity<DefaultDeleteResponseDto> deleteV1(
            @AuthenticationPrincipal Member member,
            @PathVariable Long id) {
        likeWorkbookFacade.deleteOrThrow(id, member.getId());
        return ResponseEntity.ok(
                new DefaultDeleteResponseDto("좋아요한 문제집이 성공적으로 삭제되었습니다"));
    }
}
