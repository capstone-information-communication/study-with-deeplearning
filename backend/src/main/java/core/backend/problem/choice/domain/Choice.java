package core.backend.problem.choice.domain;

import core.backend.problem.choice.dto.ChoiceUpdateRequestDto;
import core.backend.global.domain.BaseTimeEntity;
import core.backend.problem.question.domain.Question;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Choice extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Enumerated
    @Column(nullable = false)
    private State state;

    @Column(nullable = false)
    private String content;

    //- 연관관계 코드 -//
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "question_id")
    private Question question;

    //-- 연관관계 메서드 --//
    public void setQuestion(Question question) {
        if (this.question != null) {
            this.question.getChoiceList().remove(this);
        }
        this.question = question;
        this.question.getChoiceList().add(this);
    }

    //-- 비즈니스 로직 --//
    @Builder
    public Choice(State state, String content, Question question) {
        this.state = state;
        this.content = content;
        setQuestion(question);
    }

    public void update(ChoiceUpdateRequestDto dto) {
        this.state = dto.getState();
        this.content = dto.getContent();
    }
}
