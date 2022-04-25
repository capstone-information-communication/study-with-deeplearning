package core.backend.member.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Role {
    USER("일반 유저의 권한"),
    STAFF("일반 직원의 권한"),
    ADMIN("모든 권한을 가진 자");

    private final String description;
}
