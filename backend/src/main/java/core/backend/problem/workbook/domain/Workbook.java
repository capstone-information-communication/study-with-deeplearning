package core.backend.problem.workbook.domain;

import core.backend.global.domain.BaseTimeEntity;
import core.backend.problem.question.domain.Question;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Workbook extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Long likeCount;

    //- 연관관계 코드 -//
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "workbook")
    private List<Question> questionList = new ArrayList<>();

    //-- 비즈니스 로직 --//
    @Builder
    public Workbook(Long memberId, String title, String description) {
        this.memberId = memberId;
        this.title = title;
        this.description = description;
        this.likeCount = 0L;
    }

    public Long update(String title, String description) {
        this.title = title;
        this.description = description;
        return id;
    }
}