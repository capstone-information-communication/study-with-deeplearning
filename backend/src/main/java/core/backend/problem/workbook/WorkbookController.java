package core.backend.problem.workbook;

import core.backend.global.dto.DataResponse;
import core.backend.global.dto.DefaultDeleteResponseDto;
import core.backend.member.domain.Member;
import core.backend.problem.question.service.QuestionAIService;
import core.backend.problem.workbook.domain.Workbook;
import core.backend.problem.workbook.dto.*;
import core.backend.problem.workbook.service.WorkbookFacade;
import core.backend.problem.workbook.service.WorkbookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class WorkbookController {

    private final WorkbookService workbookService;
    private final WorkbookFacade workbookFacade;
    private final QuestionAIService questionAIService;

    @GetMapping("/workbook/{id}")
    public ResponseEntity<WorkbookResponseDto> findByIdV1(
            @PathVariable Long id) {
        return ResponseEntity.ok(
                new WorkbookResponseDto(workbookService.findByIdOrThrow(id)));
    }

    @GetMapping("/workbook/{id}/category")
    public ResponseEntity<WorkbookCategoryResponseDto> findByIdAndSortByCategoryV1(
            @PathVariable Long id) {
        return ResponseEntity.ok(workbookFacade.findByIdAndSortByCategory(id));
    }

    @GetMapping("/workbooks")
    public ResponseEntity<DataResponse> findAllV1(
            @PageableDefault Pageable pageable) {
        List<WorkbookResponseDto> result = workbookService.findAll(pageable)
                .stream()
                .map(WorkbookResponseDto::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                DataResponse.builder().count(result.size()).data(result).build());
    }

    @GetMapping("/workbook/search")
    public ResponseEntity<DataResponse> searchV1(
            @RequestParam(defaultValue = "") String title,
            @RequestParam(defaultValue = "") String description,
            @PageableDefault Pageable pageable) {
        List<WorkbookResponseDto> result = workbookFacade.search(
                new WorkbookCondition(title, description),
                pageable);

        return ResponseEntity.ok(
                DataResponse.builder().count(result.size()).data(result).build());
    }

    @PostMapping("/workbook")
    public ResponseEntity<WorkbookResponseDto> saveV1(
            @AuthenticationPrincipal Member member,
            @RequestBody WorkbookSaveRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                WorkbookResponseDto.builder()
                        .entity(workbookFacade.saveOrThrow(dto.toEntity(member.getId()))).build());
    }

    @PostMapping("/workbook/{id}/check")
    public ResponseEntity<WorkbookCheckResponseDto> checkV1(
            @AuthenticationPrincipal Member member,
            @PathVariable Long id,
            @RequestBody List<WorkbookCheckRequestDto> dtoList) {
        List<Integer> result = workbookFacade.checkOrThrow(id, member.getId(), dtoList);
        return ResponseEntity.ok(
                new WorkbookCheckResponseDto(result.get(0), result.get(1)));
    }

    @PostMapping("/workbook-with-text")
    public ResponseEntity<WorkbookResponseDto> saveWithTextV1(
            @AuthenticationPrincipal Member member,
            @RequestBody WorkbookWithTextRequestDto dto) {
        Workbook workbook = workbookFacade.saveOrThrow(dto.toEntity(member.getId()));
        questionAIService.requestTextAndSaveResponse(workbook.getId(), dto.getText());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new WorkbookResponseDto(workbook));
    }

    @PutMapping("/workbook/{id}")
    public ResponseEntity<WorkbookResponseDto> updateV1(
            @AuthenticationPrincipal Member member,
            @PathVariable Long id,
            @RequestBody WorkbookUpdateRequestDto dto) {
        return ResponseEntity.ok(
                WorkbookResponseDto.builder()
                        .entity(workbookFacade.updateOrThrow(id, member.getId(), dto)).build());
    }

    @DeleteMapping("/workbook/{id}")
    public ResponseEntity<DefaultDeleteResponseDto> deleteV1(
            @AuthenticationPrincipal Member member,
            @PathVariable Long id) {
        workbookFacade.deleteById(id, member.getId());
        return ResponseEntity.ok(
                new DefaultDeleteResponseDto("문제집이 성공적으로 삭제되었습니다"));
    }
}
