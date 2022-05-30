package core.backend.member.dto;

import core.backend.member.domain.Member;
import core.backend.member.domain.Role;
import core.backend.member.domain.WrongFigure;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemberResponseDto {
    private String email;
    private String name;
    private String nickname;
    private Role role;
    private WrongFigure wrongFigure;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;

    public MemberResponseDto(Member entity) {
        name = entity.getName();
        role = entity.getRole();
        email = entity.getEmail();
        nickname = entity.getNickname();
        wrongFigure = entity.getWrongFigure();
        updatedAt = entity.getUpdatedAt();
        createdAt = entity.getCreatedAt();
    }
}
