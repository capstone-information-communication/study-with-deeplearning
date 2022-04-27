package core.backend.member.dto;

import core.backend.member.domain.Member;
import core.backend.member.domain.Role;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberSignUpRequestDto {
    private String name;
    private String email;
    private Role role;
    private String password;
    private String nickname;

    public MemberSignUpRequestDto(String name, String email, Role role, String password, String nickname) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.password = password;
        this.nickname = nickname;
    }

    public Member toEntity(String password) {
        return Member.builder()
                .name(name)
                .email(email)
                .nickname(nickname)
                .password(password)
                .role(role)
                .build();
    }
}
