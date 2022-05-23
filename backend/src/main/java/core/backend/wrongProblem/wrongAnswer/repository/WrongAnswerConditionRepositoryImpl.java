package core.backend.wrongProblem.wrongAnswer.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import core.backend.global.error.exception.TotalNotFound;
import core.backend.wrongProblem.wrongAnswer.domain.WrongAnswer;
import core.backend.wrongProblem.wrongAnswer.dto.WrongAnswerCondition;
import core.backend.wrongProblem.wrongAnswer.exception.WrongAnswerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static core.backend.wrongProblem.wrongAnswer.domain.QWrongAnswer.wrongAnswer;
import static org.springframework.util.StringUtils.hasText;

@Repository
@RequiredArgsConstructor
public class WrongAnswerConditionRepositoryImpl implements WrongAnswerConditionRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<WrongAnswer> searchOrThrow(WrongAnswerCondition condition, Pageable pageable) {
        List<WrongAnswer> content = Optional.of(
                queryFactory
                        .selectFrom(wrongAnswer)
                        .where(
                                memberIdEq(condition.getMemberId()),
                                workbookIdEq(condition.getWorkbookId()),
                                questionIdEq(condition.getProblemId())
                        )
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch()
        ).orElseThrow(WrongAnswerNotFoundException::new);

        Long total = Optional.ofNullable(
                queryFactory
                        .select(wrongAnswer.count())
                        .from(wrongAnswer)
                        .fetchOne()
        ).orElseThrow(TotalNotFound::new);
        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression memberIdEq(Long memberId) {
        return hasText(String.valueOf(memberId)) ? wrongAnswer.memberId.eq(memberId) : null;
    }

    private BooleanExpression questionIdEq(Long questionId) {
        return hasText(String.valueOf(questionId)) ? wrongAnswer.questionId.eq(questionId) : null;
    }

    private BooleanExpression workbookIdEq(Long workbookId) {
        return hasText(String.valueOf(workbookId)) ? wrongAnswer.workbookId.eq(workbookId) : null;
    }
}
