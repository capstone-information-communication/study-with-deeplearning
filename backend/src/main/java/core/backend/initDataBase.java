package core.backend;

import core.backend.choice.domain.Choice;
import core.backend.choice.domain.State;
import core.backend.member.domain.Member;
import core.backend.member.domain.Role;
import core.backend.question.domain.Category;
import core.backend.question.domain.Commentary;
import core.backend.question.domain.Question;
import core.backend.question.service.QuestionService;
import core.backend.workbook.domain.Workbook;
import core.backend.workbook.service.WorkbookService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Profile("local")
public class initDataBase {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit(100);
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final WorkbookService workbookService;
        private final QuestionService questionService;

        private final EntityManager em;

        private List<Long> memberIdList = new ArrayList<>();
        private List<Long> workbookIdList = new ArrayList<>();
        private List<Long> questionIdList = new ArrayList<>();

        public void dbInit(int count) {
            for (int i = 0; i < count; i++) {
                generateMemberBy(i);
                generateWorkbookBy(i);

                generateQuestionBy(i);
                generateChoiceBy(i);
                generateChoiceBy(i);
            }
        }

        private void generateMemberBy(int i) {
            Member member = Member.builder()
                    .role(Role.USER)
                    .password(String.valueOf(i))
                    .nickname("nickname" + i)
                    .email("test" + i + "@gmail.com")
                    .name("member" + i)
                    .build();
            em.persist(member);
            memberIdList.add(member.getId());
        }

        private void generateWorkbookBy(int i) {
            Workbook workbook = Workbook.builder()
                    .memberId(memberIdList.get(i))
                    .title("title" + i)
                    .description("description" + i)
                    .build();
            em.persist(workbook);
            workbookIdList.add(workbook.getId());
        }

        private void generateQuestionBy(int i) {
            Workbook workbook = workbookService.findByIdOrThrow(workbookIdList.get(i));
            Category category = categoryFactory(i);

            Question question = Question.builder()
                    .workbook(workbook)
                    .commentary(Commentary.builder().comment("commentary" + i).build())
                    .title("title" + i)
                    .content("content" + i)
                    .category(category)
                    .build();
            em.persist(question);
            questionIdList.add(question.getId());
        }

        private Category categoryFactory(int index) {
            switch (index % 4) {
                case 0:
                    return Category.BLANK;
                case 1:
                    return Category.ORDER;
                case 2:
                    return Category.MULTIPLE;
                case 3:
                    return Category.SHORT;
            }
            return Category.ORDER;
        }

        private void generateChoiceBy(int i) {
            Question question = questionService.findByIdOrThrow(questionIdList.get(i));
            Choice choice = Choice.builder()
                    .question(question)
                    .state(State.ANSWER)
                    .content("content" + i)
                    .build();
        }
    }
}
