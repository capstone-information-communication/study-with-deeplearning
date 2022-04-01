package core.backend.member.repository;

import core.backend.member.domain.Member;
import core.backend.member.dto.MemberCondition;

import java.util.Optional;

public interface MemberCredentialRepository {
    Optional<Member> findByEmail(String email);

    Optional<Member> findByNickname(String nickname);

    Optional<Member> findByCondition(MemberCondition condition);
}
