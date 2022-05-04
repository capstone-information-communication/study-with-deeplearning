package core.backend.workbook.service;

import core.backend.choice.domain.Choice;
import core.backend.choice.domain.State;
import core.backend.question.domain.Category;
import core.backend.question.domain.Question;
import core.backend.question.dto.QuestionCategoryResponseDto;
import core.backend.question.service.QuestionService;
import core.backend.workbook.domain.Workbook;
import core.backend.workbook.dto.*;
import core.backend.workbook.exception.WorkbookExistTitleException;
import core.backend.workbook.exception.WorkbookNotAuthorException;
import core.backend.wrongAnswer.domain.WrongAnswer;
import core.backend.wrongAnswer.service.WrongAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkbookFacade {

    private final WorkbookService workbookService;
    private final QuestionService questionService;
    private final WrongAnswerService wrongAnswerService;

    public WorkbookCategoryResponseDto findByIdAndSortByCategory(Long id) {
        Workbook workbook = workbookService.findByIdOrThrow(id);
        List<QuestionCategoryResponseDto> questionList = getCategoricalMap(new HashMap<Category, List<Question>>(), workbook.getQuestionList())
                .entrySet().stream()
                .map(item -> new QuestionCategoryResponseDto(item.getKey(), item.getValue()))
                .collect(Collectors.toList());
        return new WorkbookCategoryResponseDto(workbook, questionList);
    }

    private HashMap<Category, List<Question>> getCategoricalMap(HashMap<Category, List<Question>> map, List<Question> questionList) {
        initMap(map);
        for (Question question : questionList) {
            map.get(question.getCategory()).add(question);
        }
        return map;
    }

    private void initMap(HashMap map) {
        for (Category value : Category.values()) {
            map.put(value, new ArrayList<>());
        }
    }

    public List<WorkbookResponseDto> search(WorkbookCondition condition, Pageable pageable) {
        return workbookService.search(condition, pageable)
                .stream()
                .map(WorkbookResponseDto::new)
                .collect(Collectors.toList());
    }

    public Workbook saveOrThrow(Workbook workbook) {
        isValidTitle(workbook.getTitle());
        workbookService.save(workbook);
        return workbook;
    }

    private void isValidTitle(String title) {
        workbookService.findByTitle(title)
                .ifPresent(e -> {
                    throw new WorkbookExistTitleException();
                });
    }

    public Workbook updateOrThrow(Long id, Long memberId, WorkbookUpdateRequestDto dto) {
        Workbook workbook = workbookService.findByIdOrThrow(id);
        isAuthorOrThrow(workbook, memberId);
        workbookService.update(workbook, dto);
        return workbook;
    }

    public void deleteById(Long id, Long memberId) {
        isAuthorOrThrow(
                workbookService.findByIdOrThrow(id),
                memberId);
        workbookService.deleteById(id);
    }

    private void isAuthorOrThrow(Workbook workbook, Long memberId) {
        if (!workbook.getMemberId().equals(memberId)) {
            throw new WorkbookNotAuthorException();
        }
    }

    public List<Integer> checkOrThrow(Long workbookId, Long memberId, List<WorkbookCheckRequestDto> dtoList) {
        int answer = getAnswerCountAndSaveWrongAnswer(workbookId, memberId, dtoList);
        Workbook workbook = workbookService.findByIdOrThrow(workbookId);
        return getResultList(answer, workbook);
    }

    private List<Integer> getResultList(int answer, Workbook workbook) {
        List<Integer> result = new ArrayList<>();
        result.add(answer);
        result.add(workbook.getQuestionList().size() - answer);
        return result;
    }

    private int getAnswerCountAndSaveWrongAnswer(Long workbookId, Long memberId, List<WorkbookCheckRequestDto> dtoList) {
        int answer = 0;
        for (WorkbookCheckRequestDto dto : dtoList) {
            if (check(dto.getQuestionId(), dto.getChoiceId()) == 0) {
                saveWrongAnswer(workbookId, memberId, dto);
            } else {
                answer += 1;
            }
        }
        return answer;
    }

    private void saveWrongAnswer(Long workbookId, Long memberId, WorkbookCheckRequestDto dto) {
        wrongAnswerService.save(WrongAnswer.builder()
                .workbookId(workbookId)
                .memberId(memberId)
                .questionId(dto.getQuestionId())
                .build());
    }

    private int check(Long questionId, Long choiceId) {
        Question question = questionService.findByIdOrThrow(questionId);
        for (Choice choice : question.getChoiceList()) {
            if (choice.getState().equals(State.ANSWER)) {
                if (choice.getId().equals(choiceId)) {
                    return 1;
                }
            }
        }
        return 0;
    }
}
