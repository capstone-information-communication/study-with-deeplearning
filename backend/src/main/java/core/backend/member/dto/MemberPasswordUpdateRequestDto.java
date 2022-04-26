package core.backend.member.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberPasswordUpdateRequestDto {
    private Long id;
    private String password;

    public MemberPasswordUpdateRequestDto(Long id, String password) {
        this.id = id;
        this.password = password;
    }
}
