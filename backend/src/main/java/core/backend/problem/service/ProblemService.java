package core.backend.problem.service;

import core.backend.problem.domain.Problem;
import core.backend.problem.exception.ProblemNotFound;
import core.backend.problem.repository.ProblemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProblemService {

    private final ProblemRepository problemRepository;

    @Transactional
    public Long save(Problem problem) {
        problemRepository.save(problem);
        return problem.getId();
    }

    @Transactional
    public void deleteById(Long id) {
        problemRepository.deleteById(id);
    }

    public Problem findByIdOrThrow(Long id) {
        return problemRepository.findById(id)
                .orElseThrow(ProblemNotFound::new);
    }

    public List<Problem> findAll() {
        return problemRepository.findAll();
    }
}
