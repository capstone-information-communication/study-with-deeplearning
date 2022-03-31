package core.backend.wrongAnswer.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import core.backend.global.error.exception.TotalNotFound;
import core.backend.wrongAnswer.domain.WrongAnswer;
import core.backend.wrongAnswer.dto.QWrongAnswerDto;
import core.backend.wrongAnswer.dto.WrongAnswerCondition;
import core.backend.wrongAnswer.dto.WrongAnswerDto;
import core.backend.wrongAnswer.exception.WrongAnswerNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static core.backend.wrongAnswer.domain.QWrongAnswer.wrongAnswer;
import static org.springframework.util.StringUtils.hasText;

@Repository
@RequiredArgsConstructor
public class WrongAnswerConditionRepositoryImpl implements WrongAnswerConditionRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<WrongAnswer> findByMemberIdOrThrow(Long memberId) {
        return Optional.of(
                queryFactory
                        .selectFrom(wrongAnswer)
                        .where(
                                memberIdEq(memberId)
                        )
                        .fetch()
        ).orElseThrow(WrongAnswerNotFound::new);
    }

    @Override
    public List<WrongAnswer> findByProblemIdOrThrow(Long problemId) {
        return Optional.of(
                queryFactory
                        .selectFrom(wrongAnswer)
                        .where(
                                problemIdEq(problemId)
                        )
                        .fetch()
        ).orElseThrow(WrongAnswerNotFound::new);
    }

    @Override
    public List<WrongAnswer> findByWorkbookIdOrThrow(Long workbookId) {
        return Optional.of(
                queryFactory
                        .selectFrom(wrongAnswer)
                        .where(
                                workbookIdEq(workbookId)
                        )
                        .fetch()
        ).orElseThrow(WrongAnswerNotFound::new);
    }

    @Override
    public Page<WrongAnswer> searchOrThrow(WrongAnswerCondition condition, Pageable pageable) {
        List<WrongAnswer> content = Optional.of(
                queryFactory
                        .selectFrom(wrongAnswer)
                        .where(
                                memberIdEq(condition.getMemberId()),
                                workbookIdEq(condition.getWorkbookId()),
                                problemIdEq(condition.getProblemId())
                        )
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch()
        ).orElseThrow(WrongAnswerNotFound::new);

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

    private BooleanExpression problemIdEq(Long problemId) {
        return hasText(String.valueOf(problemId)) ? wrongAnswer.problemId.eq(problemId) : null;
    }

    private BooleanExpression workbookIdEq(Long workbookId) {
        return hasText(String.valueOf(workbookId)) ? wrongAnswer.workbookId.eq(workbookId) : null;
    }
}
