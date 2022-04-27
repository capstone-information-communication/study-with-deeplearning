package core.backend.workbook;

import core.backend.global.dto.DataResponse;
import core.backend.workbook.dto.WorkbookCondition;
import core.backend.workbook.dto.WorkbookResponseDto;
import core.backend.workbook.dto.WorkbookSaveRequestDto;
import core.backend.workbook.exception.WorkbookExistTitleException;
import core.backend.workbook.exception.WorkbookNotAuthorException;
import core.backend.workbook.domain.Workbook;
import core.backend.workbook.dto.WorkbookUpdateRequestDto;
import core.backend.workbook.service.WorkbookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class WorkbookController {

    private final WorkbookService workbookService;

    @GetMapping("/workbook/{id}")
    public ResponseEntity<WorkbookResponseDto> findByIdV1(
            @PathVariable Long id) {
        return ResponseEntity.ok(
                new WorkbookResponseDto(workbookService.findByIdOrThrow(id)));
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
            @RequestParam String title,
            @RequestParam String description,
            @PageableDefault Pageable pageable) {
        WorkbookCondition condition = new WorkbookCondition(title, description);
        Page<Workbook> search = workbookService.search(condition, pageable);

        List<WorkbookResponseDto> result = search.stream()
                .map(WorkbookResponseDto::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                DataResponse.builder().count(result.size()).data(result).build());
    }

    @PostMapping("/workbook/{id}")
    public ResponseEntity<WorkbookResponseDto> saveV1(
            //TODO: spring security를 사용하여 auth를 사용하여 회원 id 가져오기
            @PathVariable Long memberId,
            @RequestBody WorkbookSaveRequestDto dto) {
        isValidTitle(dto.getTitle());

        Long id = workbookService.save(dto.toEntity(memberId));

        return ResponseEntity.status(HttpStatus.CREATED).body(
                WorkbookResponseDto.builder()
                        .entity(workbookService.findByIdOrThrow(id)).build());
    }

    private void isValidTitle(String title) {
        workbookService.findByTitleOrThrow(title)
                .ifPresent(e -> {
                    throw new WorkbookExistTitleException();
                });
    }

    @PutMapping("/workbook/{id}/{memberId}")
    public ResponseEntity<WorkbookResponseDto> updateV1(
            //TODO: spring security를 사용하여 auth를 사용하여 회원 id 가져오기 AuthenticatationPrincipal
            @PathVariable Long id,
            @PathVariable Long memberId,
            @RequestBody WorkbookUpdateRequestDto dto) {
        isAuthor(id, memberId);
        Long updatedId = workbookService.update(id, dto);

        return ResponseEntity.ok(
                WorkbookResponseDto.builder()
                        .entity(workbookService.findByIdOrThrow(updatedId)).build());
    }

    private void isAuthor(Long id, Long memberId) {
        Workbook workbook = workbookService.findByIdOrThrow(id);
        if (!workbook.getMemberId().equals(memberId)) {
            throw new WorkbookNotAuthorException();
        }
    }

    @DeleteMapping("/workbook/{id}")
    public ResponseEntity<WorkbookResponseDto> deleteV1(
            @PathVariable Long id) {
        //TODO: 문제집을 저장한 회원만 문제집을 삭제할 수 있는 코드 추가
        Workbook deletedWorkbook = workbookService.findByIdOrThrow(id);
        workbookService.deleteById(id);
        return ResponseEntity.ok(
                WorkbookResponseDto.builder()
                        .entity(deletedWorkbook).build());
    }
}
