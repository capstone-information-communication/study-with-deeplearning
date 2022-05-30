package core.backend.wrongProblem.wrongQuestion;

import core.backend.global.dto.DataResponse;
import core.backend.global.dto.DefaultDeleteResponseDto;
import core.backend.member.domain.Member;
import core.backend.wrongProblem.wrongQuestion.dto.WrongQuestionResponseDto;
import core.backend.wrongProblem.wrongQuestion.service.WrongQuestionService;
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
public class WrongQuestionController {
    private WrongQuestionService wrongQuestionService;

    @GetMapping("/wrong-question/{id}")
    public ResponseEntity<WrongQuestionResponseDto> findByIdV1(
            @PathVariable Long id) {
        return ResponseEntity.ok(
                new WrongQuestionResponseDto(wrongQuestionService.findByIdOrThrow(id)));
    }

    @GetMapping("/wrong-questions")
    public ResponseEntity<DataResponse> findAllV1(
            @PageableDefault Pageable pageable) {
        List<WrongQuestionResponseDto> result = wrongQuestionService.findAll(pageable)
                .stream()
                .map(WrongQuestionResponseDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(
                DataResponse.builder().data(result).count(result.size()).build());
    }

    @DeleteMapping("/wrong-question/{id}")
    public ResponseEntity<DefaultDeleteResponseDto> deleteByIdV1(
            @PathVariable Long id,
            @AuthenticationPrincipal Member member) {
        wrongQuestionService.deleteByIdOrThrow(id, member.getId());
        return ResponseEntity.ok(
                new DefaultDeleteResponseDto("오답 문제가 삭제되었습니다"));
    }
}
