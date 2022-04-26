package core.backend.commentary.domain;

import core.backend.commentary.dto.CommentaryUpdateRequestDto;
import core.backend.global.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Commentary extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String content;

    //-- 비즈니스 로직 --//
    @Builder
    public Commentary(String content) {
        this.content = content;
    }

    public void update(CommentaryUpdateRequestDto dto) {
        this.content = dto.getContent();
    }
}
