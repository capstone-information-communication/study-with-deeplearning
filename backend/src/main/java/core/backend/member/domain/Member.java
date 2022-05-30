package core.backend.member.domain;

import core.backend.global.domain.BaseTimeEntity;
import core.backend.member.dto.MemberUpdateRequestDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Embedded
    @Column(nullable = false)
    private WrongFigure wrongFigure;

    //-- 비즈니스 로직 --//
    @Builder
    public Member(Role role, String name, String nickname, String email, String password) {
        this.role = role;
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        wrongFigure = new WrongFigure();
    }

    public void update(MemberUpdateRequestDto dto) {
        this.email = dto.getEmail();
        this.role = dto.getRole();
        this.nickname = dto.getNickname();
    }

    public void updateWrongFigure(WrongFigure wrongFigure) {
        this.wrongFigure = wrongFigure;
    }

    public void updatePassword(String password, PasswordEncoder encoder) {
        this.password = encoder.encode(password);
    }
}
