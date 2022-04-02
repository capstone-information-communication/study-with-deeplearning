package core.backend.workbook.domain;

import core.backend.problem.domain.Problem;
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
public class Workbook {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "workbook")
    private List<Problem> problemList = new ArrayList<>();

    //-- 비즈니스 로직 --//
    @Builder
    public Workbook(Long memberId, String title, String description, List<Problem> problemList) {
        this.memberId = memberId;
        this.title = title;
        this.description = description;
        this.problemList = problemList;
    }

    public Long update(String title, String description) {
        this.title = title;
        this.description = description;
        return id;
    }
}