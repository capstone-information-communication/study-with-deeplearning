package core.backend.wrongProblem.wrongWorkbook.domain;

import core.backend.global.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WrongWorkbook extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private Long id;

    private Long memberId;

}