package core.backend.member.service;

import core.backend.member.domain.Member;
import core.backend.member.domain.WrongFigure;
import core.backend.problem.question.domain.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WrongFigureServiceImpl implements WrongFigureService {

    private final MemberService memberService;

    @Transactional
    public Long update(Long id, Category category) {
        Member members = memberService.findByIdOrThrow(id);
        members.getWrongFigure();
        WrongFigure wrongFigure = memberService
                .findByIdOrThrow(id)
                .getWrongFigure();
        wrongFigure.addByCategory(category, 1);
        return id;
    }
}
