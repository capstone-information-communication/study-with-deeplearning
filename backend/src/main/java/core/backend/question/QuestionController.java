package core.backend.question;

import core.backend.global.dto.DataResponse;
import core.backend.question.domain.Category;
import core.backend.question.dto.QuestionResponseDto;
import core.backend.question.dto.QuestionUpdateRequestDto;
import core.backend.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
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
public class QuestionController {

    private final QuestionService questionService;

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

    @PutMapping("/question/{id}")
    public ResponseEntity<QuestionResponseDto> updateV1(
            @PathVariable Long id,
            @RequestBody QuestionUpdateRequestDto dto) {
        return ResponseEntity.ok(
                new QuestionResponseDto(questionService.update(id, dto)));
    }

    @DeleteMapping("/question/{id}")
    public HttpStatus deleteByIdV1(
            @PathVariable Long id) {
        questionService.deleteById(id);
        return HttpStatus.OK;
    }
}
