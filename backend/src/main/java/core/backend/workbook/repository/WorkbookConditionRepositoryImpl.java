package core.backend.workbook.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import core.backend.global.error.exception.TotalNotFound;
import core.backend.workbook.dto.WorkbookCondition;
import core.backend.workbook.entity.Workbook;
import core.backend.workbook.exception.WorkbookNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static core.backend.workbook.entity.QWorkbook.workbook;
import static org.springframework.util.StringUtils.hasText;

@Repository
@RequiredArgsConstructor
public class WorkbookConditionRepositoryImpl implements WorkbookConditionRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Workbook> findByIdOrThrow(Long id) {
        return Optional.of(
                queryFactory
                        .selectFrom(workbook)
                        .where(
                                idEq(id)
                        )
                        .fetch()
        ).orElseThrow(WorkbookNotFound::new);
    }

    @Override
    public List<Workbook> findByTitleOrThrow(String title) {
        return Optional.of(
                queryFactory
                        .selectFrom(workbook)
                        .where(
                                titleEq(title)
                        )
                        .fetch()
        ).orElseThrow(WorkbookNotFound::new);
    }

    @Override
    public List<Workbook> findByDescriptionOrThrow(String description) {
        return Optional.of(
                queryFactory
                        .selectFrom(workbook)
                        .where(
                                descriptionEq(description)
                        )
                        .fetch()
        ).orElseThrow(WorkbookNotFound::new);
    }

    @Override
    public Page<Workbook> searchOrThrow(WorkbookCondition condition, Pageable pageable) {
        List<Workbook> content = Optional.of(
                queryFactory
                        .selectFrom(workbook)
                        .where(
                                titleEq(condition.getTitle()),
                                descriptionEq(condition.getDescription())
                        )
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch()
        ).orElseThrow(WorkbookNotFound::new);

        Long total = Optional.ofNullable(
                queryFactory
                        .select(workbook.count())
                        .from(workbook)
                        .fetchOne()
        ).orElseThrow(TotalNotFound::new);
        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression idEq(Long id) {
        return hasText(String.valueOf(id)) ? workbook.id.eq(id) : null;
    }

    private BooleanExpression titleEq(String title) {
        return hasText(title) ? workbook.title.eq(title) : null;
    }

    private BooleanExpression descriptionEq(String description) {
        return hasText(description) ? workbook.description.eq(description) : null;
    }
}
