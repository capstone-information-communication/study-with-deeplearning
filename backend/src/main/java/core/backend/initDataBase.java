package core.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.backend.problem.choice.domain.Choice;
import core.backend.problem.choice.domain.ChoiceData;
import core.backend.problem.choice.domain.State;
import core.backend.likeWorkbook.domain.LikeWorkbook;
import core.backend.likeWorkbook.service.LikeWorkbookService;
import core.backend.member.domain.Member;
import core.backend.member.domain.Role;
import core.backend.problem.question.domain.Category;
import core.backend.problem.question.domain.Commentary;
import core.backend.problem.question.domain.Question;
import core.backend.problem.question.domain.QuestionData;
import core.backend.problem.question.service.QuestionService;
import core.backend.problem.workbook.domain.Workbook;
import core.backend.problem.workbook.domain.WorkbookData;
import core.backend.problem.workbook.service.WorkbookService;
import core.backend.wrongProblem.wrongAnswer.domain.WrongAnswer;
import core.backend.wrongProblem.wrongAnswer.service.WrongAnswerService;
import core.backend.wrongProblem.wrongQuestion.domain.WrongQuestion;
import core.backend.wrongProblem.wrongWorkbook.domain.WrongWorkbook;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Profile("local")
public class initDataBase {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit(10);
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final WorkbookService workbookService;
        private final QuestionService questionService;
        private final WrongAnswerService wrongAnswerService;
        private final LikeWorkbookService likeWorkbookService;

        private final PasswordEncoder encoder = new BCryptPasswordEncoder();

        private final EntityManager em;

        private final List<Long> memberIdList = new ArrayList<>();
        private final List<Long> workbookIdList = new ArrayList<>();
        private final List<Long> questionIdList = new ArrayList<>();

        public void dbInit(int count) {
            for (int i = 0; i < count; i++) {
                generateMemberBy(i);
                generateWorkbookBy(i);

                generateQuestionBy(i);
                generateQuestionBy(i);
                generateQuestionBy(i);

                generateChoiceBy(i);
                generateChoiceBy(i);

                generateLikeWorkbook(i);
                generateWrongAnswer(i);
                generateWrongWorkbook(i);
            }
        }

        private void generateMemberBy(int i) {
            Member member = Member.builder()
                    .role(Role.USER)
                    .password(encoder.encode(String.valueOf(i)))
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

            Question question = Question.builder()
                    .workbook(workbook)
                    .commentary(Commentary.builder().comment("commentary" + i).build())
                    .title("title" + i)
                    .content("content" + i)
                    .category(randomCategory())
                    .build();
            em.persist(question);
            questionIdList.add(question.getId());
        }

        private Category randomCategory() {
            switch ((int) (Math.random() * 100) % 4) {
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
                    .state(randomState())
                    .content("content" + i)
                    .build();
        }

        private State randomState() {
            if ((int) (Math.random() * 100) % 5 == 0) {
                return State.ANSWER;
            }
            return State.WRONG;
        }

        private void generateWrongAnswer(int i) {
            Workbook workbook = workbookService.findByIdOrThrow(workbookIdList.get(i));

            List<Long> questionIdList = workbook.getQuestionList()
                    .stream()
                    .map(question -> getQuestionIdList(question, 2))
                    .collect(Collectors.toList());

            List<WrongAnswer> result = questionIdList.stream()
                    .filter(Objects::nonNull)
                    .map(id -> save(id, workbook.getId(), memberIdList.get(i)))
                    .collect(Collectors.toList());
        }

        private Long getQuestionIdList(Question question, int division) {
            return question.getId() % division == 0 ? question.getId() : null;
        }

        private WrongAnswer save(Long questionId, Long workbookId, Long memberId) {
            WrongAnswer wrongAnswer = WrongAnswer.builder()
                    .questionId(questionId)
                    .workbookId(workbookId)
                    .memberId(memberId)
                    .build();
            return wrongAnswerService.save(wrongAnswer);
        }

        private void generateLikeWorkbook(int i) {
            Workbook workbook = workbookService.findByIdOrThrow(workbookIdList.get(i));

            LikeWorkbook likeWorkbook = LikeWorkbook.builder()
                    .workbookId(workbook.getId())
                    .memberId(memberIdList.get(i))
                    .build();
            likeWorkbookService.save(likeWorkbook);
        }

        private void generateWrongWorkbook(int i) {
            Workbook workbook = workbookService.findByIdOrThrow(workbookIdList.get(i));
            WrongWorkbook wrongWorkbook = WrongWorkbook.builder()
                    .memberId(memberIdList.get(i))
                    .workbook(new WorkbookData(workbook))
                    .build();
            em.persist(wrongWorkbook);
            generateWrongQuestion(i, wrongWorkbook);
        }

        private void generateWrongQuestion(int i, WrongWorkbook wrongWorkbook) {
            Question question = questionService.findByIdOrThrow(questionIdList.get(i));
            WrongQuestion wrongQuestion2 = WrongQuestion.builder()
                    .question(new QuestionData(question))
                    .wrongWorkbook(wrongWorkbook)
                    .build();
            em.persist(wrongQuestion2);
        }
    }
}
