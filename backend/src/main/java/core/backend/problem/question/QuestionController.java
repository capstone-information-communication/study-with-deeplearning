package core.backend.problem.question;

import core.backend.global.dto.DataResponse;
import core.backend.global.dto.DefaultDeleteResponseDto;
import core.backend.problem.question.dto.QuestionResponseDto;
import core.backend.problem.question.service.QuestionService;
import core.backend.problem.workbook.service.WorkbookService;
import core.backend.problem.question.domain.Category;
import core.backend.problem.question.dto.QuestionSaveRequestDto;
import core.backend.problem.question.dto.QuestionUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class QuestionController {

    private final QuestionService questionService;
    private final WorkbookService workbookService;

    @GetMapping("/question/{id}")
    public ResponseEntity<QuestionResponseDto> findByIdV1(
            @PathVariable Long id) {
        return ResponseEntity.ok(new QuestionResponseDto(questionService.findByIdOrThrow(id)));
    }

    @GetMapping("/questions")
    public ResponseEntity<DataResponse> findAllV1(
            @PageableDefault Pageable pageable) {
        List<QuestionResponseDto> result = questionService.findAll(pageable)
                .stream()
                .map(QuestionResponseDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(
                DataResponse.builder().data(result).count(result.size()).build());
    }

    @GetMapping("/question")
    public ResponseEntity<DataResponse> findByCategory(
            @RequestParam Category category,
            @PageableDefault Pageable pageable) {
        List<QuestionResponseDto> result = questionService.findByCategory(category, pageable)
                .stream()
                .map(QuestionResponseDto::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                DataResponse.builder().data(result).count(result.size()).build());
    }

    @PostMapping("/question")
    public ResponseEntity<QuestionResponseDto> saveV1(
            @RequestBody QuestionSaveRequestDto dto) {
        return ResponseEntity.status(201)
                .body(new QuestionResponseDto(
                        questionService.findByIdOrThrow(saveAndGetQuestionId(dto))));
    }

    @PostMapping("/questions")
    public ResponseEntity<DataResponse> saveListV1(
            @RequestBody List<QuestionSaveRequestDto> dtoList) {
        List<QuestionResponseDto> result = saveAndGetQuestionIdList(dtoList).stream()
                .map(id -> new QuestionResponseDto(
                        questionService.findByIdOrThrow(id)))
                .collect(Collectors.toList());
        return ResponseEntity.status(201)
                .body(DataResponse.builder().data(result).count(result.size()).build());
    }

    private List<Long> saveAndGetQuestionIdList(List<QuestionSaveRequestDto> dtoList) {
        return dtoList.stream()
                .map(this::saveAndGetQuestionId)
                .collect(Collectors.toList());
    }

    private Long saveAndGetQuestionId(QuestionSaveRequestDto dto) {
        return questionService.save(
                dto.toEntity(
                        workbookService.findByIdOrThrow(dto.getWorkbookId())));
    }

    @PutMapping("/question/{id}")
    public ResponseEntity<QuestionResponseDto> updateV1(
            @PathVariable Long id,
            @RequestBody QuestionUpdateRequestDto dto) {
        return ResponseEntity.ok(
                new QuestionResponseDto(questionService.update(id, dto)));
    }

    @DeleteMapping("/question/{id}")
    public ResponseEntity<DefaultDeleteResponseDto> deleteByIdV1(
            @PathVariable Long id) {
        questionService.findByIdOrThrow(id);
        questionService.deleteById(id);
        return ResponseEntity.ok(
                new DefaultDeleteResponseDto("질문이 성공적으로 삭제되었습니다"));
    }
}
