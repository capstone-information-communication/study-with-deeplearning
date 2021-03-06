package core.backend.problem.workbook.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import core.backend.global.error.exception.TotalNotFound;
import core.backend.problem.workbook.domain.Workbook;
import core.backend.problem.workbook.dto.WorkbookCondition;
import core.backend.problem.workbook.exception.WorkbookNotAuthorException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static core.backend.problem.workbook.domain.QWorkbook.workbook;
import static org.springframework.util.StringUtils.hasText;

@Repository
@RequiredArgsConstructor
public class WorkbookConditionRepositoryImpl implements WorkbookConditionRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Workbook> findAllWithOrderBy(String title, Pageable pageable) {
        List<Workbook> result = Optional.of(
                queryFactory
                        .selectFrom(workbook)
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .orderBy(orderByTitleAsc(title))
                        .fetch()
        ).orElseThrow(WorkbookNotAuthorException::new);
        return new PageImpl<>(result, pageable, getWorkbookTotalCount());
    }

    @Override
    public Page<Workbook> searchOrThrow(WorkbookCondition condition, Pageable pageable) {
        List<Workbook> content = Optional.of(
                queryFactory
                        .selectFrom(workbook)
                        .where(
                                titleContains(condition.getTitle()),
                                descriptionContains(condition.getDescription())
                        )
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch()
        ).orElseThrow(WorkbookNotAuthorException::new);
        return new PageImpl<>(content, pageable, getWorkbookTotalCount());
    }

    private Long getWorkbookTotalCount() {
        return Optional.ofNullable(
                queryFactory
                        .select(workbook.count())
                        .from(workbook)
                        .fetchOne()
        ).orElseThrow(TotalNotFound::new);
    }

    private BooleanExpression titleEq(String title) {
        return hasText(title) ? workbook.title.eq(title) : null;
    }

    private OrderSpecifier<String> orderByTitleAsc(String title) {
        return hasText(title) ? workbook.title.asc() : null;
    }

    private BooleanExpression titleContains(String title) {
        return hasText(title) ? workbook.title.contains(title) : null;
    }

    private BooleanExpression descriptionContains(String description) {
        return hasText(description) ? workbook.description.contains(description) : null;
    }
}
