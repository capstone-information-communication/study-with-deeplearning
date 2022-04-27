package core.backend.member.dto;

import lombok.Getter;

@Getter
public class MemberSignInResponseDto {
    private String token;

    public MemberSignInResponseDto(String token) {
        this.token = token;
    }
}
