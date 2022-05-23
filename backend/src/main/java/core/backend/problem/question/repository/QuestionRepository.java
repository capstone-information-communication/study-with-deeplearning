package core.backend.problem.question.repository;

import core.backend.problem.question.domain.Category;
import core.backend.problem.question.domain.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Page<Question> findByCategory(Category category, Pageable pageable);
}
