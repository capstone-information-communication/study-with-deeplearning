package core.backend.wrongAnswer.repository;

import core.backend.wrongAnswer.domain.WrongAnswer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WrongAnswerRepository extends JpaRepository<WrongAnswer, Long> {
    Page<WrongAnswer> findByMemberId(Long memberId, Pageable pageable);

    Page<WrongAnswer> findByQuestionId(Long QuestionId, Pageable pageable);

    Page<WrongAnswer> findByWorkbookId(Long workbookId, Pageable pageable);
}
