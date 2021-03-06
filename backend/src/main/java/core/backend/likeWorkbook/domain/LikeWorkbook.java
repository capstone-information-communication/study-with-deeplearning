package core.backend.likeWorkbook.domain;

import core.backend.global.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeWorkbook extends BaseTimeEntity {
    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private Long workbookId;

    //-- 비즈니스 로직 --//
    @Builder
    public LikeWorkbook(Long memberId, Long workbookId) {
        this.memberId = memberId;
        this.workbookId = workbookId;
    }
}
