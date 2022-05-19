package core.backend.wrongAnswer;

import core.backend.global.dto.DataResponse;
import core.backend.global.dto.DefaultDeleteResponseDto;
import core.backend.member.domain.Member;
import core.backend.wrongAnswer.dto.WrongAnswerInfoResponseDto;
import core.backend.wrongAnswer.dto.WrongAnswerResponseDto;
import core.backend.wrongAnswer.dto.WrongAnswerSaveRequestDto;
import core.backend.wrongAnswer.service.WrongAnswerFacade;
import core.backend.wrongAnswer.service.WrongAnswerService;
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
public class WrongAnswerController {

    private final WrongAnswerService wrongAnswerService;
    private final WrongAnswerFacade wrongAnswerFacade;

    @GetMapping("/wrong-answer/workbook")
    public ResponseEntity<DataResponse> findByMemberId(
            @AuthenticationPrincipal Member member,
            @PageableDefault Pageable pageable) {
        List<WrongAnswerInfoResponseDto> result = wrongAnswerFacade.findMyWrongAnswer(member.getId(), pageable);
        return ResponseEntity.ok(
                DataResponse.builder().data(result).count(result.size()).build());
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
    public ResponseEntity<DefaultDeleteResponseDto> deleteByIdV1(
            @AuthenticationPrincipal Member member,
            @PathVariable Long id) {
        wrongAnswerFacade.deleteByIdOrThrow(member.getId(), id);
        return ResponseEntity.ok(
                new DefaultDeleteResponseDto("오답노트가 성공적으로 삭제되었습니다")
        );
    }
}
