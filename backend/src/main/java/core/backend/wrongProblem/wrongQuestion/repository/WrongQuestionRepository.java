package core.backend.wrongProblem.wrongQuestion.repository;

import core.backend.wrongProblem.wrongQuestion.domain.WrongQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WrongQuestionRepository extends JpaRepository<WrongQuestion, Long> {
}
