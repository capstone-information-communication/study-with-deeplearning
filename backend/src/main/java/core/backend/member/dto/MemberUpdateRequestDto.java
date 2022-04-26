package core.backend.member.dto;

import core.backend.member.domain.Role;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberUpdateRequestDto {
    private String email;
    private Role role;
    private String nickname;

    public MemberUpdateRequestDto(String email, Role role, String nickname) {
        this.email = email;
        this.role = role;
        this.nickname = nickname;
    }
}
