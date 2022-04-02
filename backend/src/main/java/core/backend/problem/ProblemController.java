package core.backend.problem;

import core.backend.global.dto.DataResponse;
import core.backend.problem.domain.Problem;
import core.backend.problem.dto.ProblemResponseDto;
import core.backend.problem.dto.ProblemSaveRequestDto;
import core.backend.problem.dto.ProblemUpdateRequestDto;
import core.backend.problem.service.ProblemService;
import core.backend.workbook.domain.Workbook;
import core.backend.workbook.repository.WorkbookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProblemController {

    private final WorkbookRepository workbookRepository;
    private final ProblemService problemService;

    @GetMapping("/problem/{id}")
    public ProblemResponseDto findById(
            @PathVariable Long id) {
        return new ProblemResponseDto(problemService.findByIdOrThrow(id));
    }

    @GetMapping("/problems")
    public DataResponse findAll() {
        List<ProblemResponseDto> result = problemService.findAll()
                .stream()
                .map(ProblemResponseDto::new)
                .collect(Collectors.toList());
        return DataResponse.builder().count(result.size()).data(result).build();
    }

    @PostMapping("/problem")
    public ProblemResponseDto saveProblemV1(
            @RequestBody ProblemSaveRequestDto dto) {
        return saveProblemBy(dto);
    }

    @PostMapping("/problems")
    public DataResponse saveProblemsV1(
            @RequestBody List<ProblemSaveRequestDto> dtoList) {
        List<ProblemResponseDto> result = dtoList.stream()
                .map(this::saveProblemBy)
                .collect(Collectors.toList());
        return DataResponse.builder().count(result.size()).data(result).build();
    }

    private ProblemResponseDto saveProblemBy(ProblemSaveRequestDto dto) {
        Workbook workbook = workbookRepository.findById(dto.getWorkbookId()).get();
        Long id = problemService.save(dto.toEntity(workbook));
        return new ProblemResponseDto(problemService.findByIdOrThrow(id));
    }

    @PutMapping("/problem")
    public ProblemResponseDto updateV1(
            @RequestBody ProblemUpdateRequestDto dto) {
        Problem problem = problemService.findByIdOrThrow(dto.getId());
        return new ProblemResponseDto(problem.update(dto.getQuestion(), dto.getAnswer()));
    }

    @DeleteMapping("/problem/{id}")
    public ProblemResponseDto deleteV1(
            @PathVariable Long id) {
        problemService.deleteById(id);
        return new ProblemResponseDto(problemService.findByIdOrThrow(id));
    }
}
