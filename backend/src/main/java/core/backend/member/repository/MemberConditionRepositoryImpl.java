package core.backend.member.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import core.backend.member.domain.Member;
import core.backend.member.dto.MemberCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.Optional;

import static core.backend.member.domain.QMember.member;

@Repository
@RequiredArgsConstructor
public class MemberConditionRepositoryImpl implements MemberConditionRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Member> findByCondition(MemberCondition condition) {
        return Optional.ofNullable(
                queryFactory
                        .selectFrom(member)
                        .where(
                                emailOrNicknameEq(condition)
                        )
                        .fetchOne()
        );
    }

    private BooleanExpression emailEq(String email) {
        return StringUtils.hasText(email) ? member.email.eq(email) : null;
    }

    private BooleanExpression nicknameEq(String nickname) {
        return StringUtils.hasText(nickname) ? member.nickname.eq(nickname) : null;
    }

    private BooleanExpression emailOrNicknameEq(MemberCondition condition) {
        return emailEq(condition.getEmail()).or(nicknameEq(condition.getNickname()));
    }
}