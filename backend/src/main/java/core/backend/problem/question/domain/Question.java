package core.backend.problem.question.domain;

import core.backend.problem.choice.domain.Choice;
import core.backend.global.domain.BaseTimeEntity;
import core.backend.problem.workbook.domain.Workbook;
import core.backend.problem.question.dto.QuestionUpdateRequestDto;
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
public class Question extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Embedded
    private Commentary commentary;

    //- 연관관계 코드 -//
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "question",
            cascade = CascadeType.PERSIST,
            orphanRemoval = true)
    private List<Choice> choiceList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workbook_id")
    private Workbook workbook;

    //-- 연관관계 메서드 --//
    public void setWorkbook(Workbook workbook) {
        if (this.workbook != null) {
            this.workbook.getQuestionList().remove(this);
        }
        this.workbook = workbook;
        workbook.getQuestionList().add(this);
    }

    //-- 비즈니스 로직 --//
    @Builder
    public Question(String title, String content, Category category, Workbook workbook, Commentary commentary) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.commentary = commentary;
        setWorkbook(workbook);
    }

    public void update(QuestionUpdateRequestDto dto) {
        title = dto.getTitle();
        content = dto.getContent();
        category = dto.getCategory();
        commentary = dto.getCommentary();
    }
}
