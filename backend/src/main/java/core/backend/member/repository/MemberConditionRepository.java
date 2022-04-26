package core.backend.member.repository;

import core.backend.member.domain.Member;
import core.backend.member.dto.MemberCondition;

import java.util.Optional;

public interface MemberConditionRepository {
    Optional<Member> findByCondition(MemberCondition condition);
}
