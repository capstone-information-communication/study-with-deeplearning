package core.backend.wrongProblem.wrongWorkbook;

import core.backend.global.dto.DataResponse;
import core.backend.global.dto.DefaultDeleteResponseDto;
import core.backend.member.domain.Member;
import core.backend.wrongProblem.wrongWorkbook.dto.WrongWorkbookRequestDto;
import core.backend.wrongProblem.wrongWorkbook.dto.WrongWorkbookResponseDto;
import core.backend.wrongProblem.wrongWorkbook.service.WrongWorkbookFacade;
import core.backend.wrongProblem.wrongWorkbook.service.WrongWorkbookService;
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
public class WrongWorkbookController {

    private final WrongWorkbookService wrongWorkbookService;
    private final WrongWorkbookFacade wrongWorkbookFacade;

    @GetMapping("/wrong-workbook/{id}")
    public ResponseEntity<WrongWorkbookResponseDto> findByIdV1(
            @PathVariable Long id) {
        return ResponseEntity.ok(
                new WrongWorkbookResponseDto(wrongWorkbookService.findByIdOrThrow(id)));
    }

    @GetMapping("/wrong-workbooks")
    public ResponseEntity<DataResponse> findAllV1(
            @PageableDefault Pageable pageable) {
        List<WrongWorkbookResponseDto> result = wrongWorkbookService.findAll(pageable)
                .stream()
                .map(WrongWorkbookResponseDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(
                DataResponse.builder().data(result).count(result.size()).build());
    }

    @GetMapping("/my/wrong-workbook")
    public ResponseEntity<DataResponse> findAllByMemberIdV1(
            @AuthenticationPrincipal Member member) {
        List<WrongWorkbookResponseDto> result = wrongWorkbookService.findAllByMemberId(member.getId())
                .stream()
                .map(WrongWorkbookResponseDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(
                DataResponse.builder().data(result).count(result.size()).build());
    }

    @PostMapping("/wrong-workbook")
    public ResponseEntity<WrongWorkbookResponseDto> saveAllV1(
            @AuthenticationPrincipal Member member,
            @RequestBody WrongWorkbookRequestDto dto) {
        return ResponseEntity.status(201)
                .body(new WrongWorkbookResponseDto(
                        wrongWorkbookFacade.saveWrongWorkbookAndQuestion(member.getId(), dto)));
    }


    @DeleteMapping("wrong-workbook/{id}")
    public ResponseEntity<DefaultDeleteResponseDto> deleteByIdV1(
            @PathVariable Long id,
            @AuthenticationPrincipal Member member) {
        wrongWorkbookService.deleteByIdOrThrow(id, member.getId());
        return ResponseEntity.ok(
                new DefaultDeleteResponseDto("오답 문제집이 삭제되었습니다"));
    }

}
