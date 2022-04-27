package core.backend.wrongAnswer;

import core.backend.global.dto.DataResponse;
import core.backend.member.domain.Member;
import core.backend.workbook.service.WorkbookService;
import core.backend.wrongAnswer.dto.WrongAnswerInfoResponseDto;
import core.backend.wrongAnswer.dto.WrongAnswerResponseDto;
import core.backend.wrongAnswer.dto.WrongAnswerSaveRequestDto;
import core.backend.wrongAnswer.service.WrongAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class WrongAnswerController {

    private final WrongAnswerService wrongAnswerService;
    private final WorkbookService workbookService;

    @GetMapping("/wrong-answer/workbook")
    public ResponseEntity<DataResponse> findByMemberId(
            @AuthenticationPrincipal Member member,
            @PageableDefault Pageable pageable) {
        List<WrongAnswerInfoResponseDto> result = getWrongAnswerMap(member.getId(), pageable)
                .entrySet().stream()
                .map(entry ->
                        new WrongAnswerInfoResponseDto(
                                workbookService.findByIdOrThrow(entry.getKey()),
                                entry.getValue()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(
                DataResponse.builder().data(result).count(result.size()).build());
    }

    private HashMap<Long, List<Long>> getWrongAnswerMap(Long memberId, Pageable pageable) {
        HashMap<Long, List<Long>> wrongAnswerMap = new HashMap<>();
        wrongAnswerService.findByMemberId(memberId, pageable)
                .stream()
                .map(wrongAnswer -> setKeyAndValue(
                        wrongAnswerMap,
                        wrongAnswer.getWorkbookId(),
                        wrongAnswer.getQuestionId()));
        return wrongAnswerMap;
    }

    private int setKeyAndValue(HashMap<Long, List<Long>> wrongAnswerMap, Long workbookId, Long questionId) {
        if (wrongAnswerMap.containsKey(workbookId)) {
            wrongAnswerMap.get(workbookId).add(questionId);
        } else {
            wrongAnswerMap.put(workbookId, new ArrayList<>());
            wrongAnswerMap.get(workbookId).add(questionId);
        }
        return 1;
    }

    @PostMapping("/wrong-answer")
    public ResponseEntity<WrongAnswerResponseDto> saveV1(
            @AuthenticationPrincipal Member member,
            @RequestBody WrongAnswerSaveRequestDto dto) {
        return ResponseEntity.status(201)
                .body(new WrongAnswerResponseDto(wrongAnswerService.save(dto.toEntity(member.getId()))));
    }

    @PostMapping("/wrong-answers")
    public ResponseEntity<DataResponse> saveListV1(
            @AuthenticationPrincipal Member member,
            @RequestBody List<WrongAnswerSaveRequestDto> dtoList) {
        List<WrongAnswerResponseDto> result = dtoList.stream()
                .map(dto -> new WrongAnswerResponseDto(wrongAnswerService.save(dto.toEntity(member.getId()))))
                .collect(Collectors.toList());
        return ResponseEntity.status(201)
                .body(DataResponse.builder().data(result).count(result.size()).build());
    }

    @DeleteMapping("/wrong-answer/{id}")
    public HttpStatus deleteByIdV1(
            @PathVariable Long id) {
        wrongAnswerService.deleteById(id);
        return HttpStatus.OK;
    }
}
