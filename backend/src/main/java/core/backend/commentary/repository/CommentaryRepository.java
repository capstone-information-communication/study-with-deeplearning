package core.backend.commentary.repository;

import core.backend.commentary.domain.Commentary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentaryRepository extends JpaRepository<Commentary, Long> {
}
