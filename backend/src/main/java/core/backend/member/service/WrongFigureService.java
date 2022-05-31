package core.backend.member.service;

import core.backend.problem.question.domain.Category;

public interface WrongFigureService {
    Long update(Long memberId, Category category);
}
