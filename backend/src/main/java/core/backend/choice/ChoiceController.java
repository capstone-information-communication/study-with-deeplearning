package core.backend.choice;

import core.backend.choice.domain.Choice;
import core.backend.choice.dto.ChoiceResponseDto;
import core.backend.choice.dto.ChoiceSaveRequestDto;
import core.backend.choice.dto.ChoiceUpdateRequestDto;
import core.backend.choice.service.ChoiceService;
import core.backend.global.dto.DataResponse;
import core.backend.global.dto.DefaultDeleteResponseDto;
import core.backend.question.domain.Question;
import core.backend.question.service.QuestionService;
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
public class ChoiceController {

    private final ChoiceService choiceService;
    private final QuestionService questionService;

    @GetMapping("/choice/{id}")
    public ResponseEntity<ChoiceResponseDto> findByIdV1(
            @PathVariable Long id) {
        return ResponseEntity.ok(
                new ChoiceResponseDto(choiceService.findByIdOrThrow(id)));
    }

    @GetMapping("/choices")
    public ResponseEntity<DataResponse> findAllV1(
            @PageableDefault Pageable pageable) {
        List<ChoiceResponseDto> result = choiceService.findAll(pageable)
                .stream()
                .map(ChoiceResponseDto::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                DataResponse.builder().data(result).count(result.size()).build());
    }

    @PostMapping("/choice")
    public ResponseEntity<ChoiceResponseDto> saveV1(
            @RequestBody ChoiceSaveRequestDto dto) {
        return ResponseEntity.status(201)
                .body(new ChoiceResponseDto(choiceService.findByIdOrThrow(saveChoiceBy(dto))));
    }

    @PostMapping("/choices")
    public ResponseEntity<DataResponse> saveListV1(
            @RequestBody List<ChoiceSaveRequestDto> dtoList) {
        List<ChoiceResponseDto> result = saveAndGetChoiceIdList(dtoList).stream()
                .map(id -> new ChoiceResponseDto(choiceService.findByIdOrThrow(id)))
                .collect(Collectors.toList());

        return ResponseEntity.status(201)
                .body(DataResponse.builder().data(result).count(result.size()).build());
    }

    private List<Long> saveAndGetChoiceIdList(List<ChoiceSaveRequestDto> dtoList) {
        return dtoList.stream()
                .map(this::saveChoiceBy).collect(Collectors.toList());
    }

    private Long saveChoiceBy(ChoiceSaveRequestDto dto) {
        Question question = questionService.findByIdOrThrow(dto.getQuestionId());
        return choiceService.save(dto.toEntity(question));
    }

    @PutMapping("/choice/{id}")
    public ResponseEntity<ChoiceResponseDto> updateV1(
            @PathVariable Long id,
            @RequestBody ChoiceUpdateRequestDto dto) {
        Choice choice = choiceService.findByIdOrThrow(id);
        choice.update(dto);
        return ResponseEntity.ok(
                new ChoiceResponseDto(choice));
    }

    @DeleteMapping("/choice/{id}")
    public ResponseEntity<DefaultDeleteResponseDto> deleteByIdV1(
            @PathVariable Long id) {
        choiceService.deleteById(id);
        return ResponseEntity.ok(
                new DefaultDeleteResponseDto("질문이 성공적으로 삭제되었습니다"));
    }
}
