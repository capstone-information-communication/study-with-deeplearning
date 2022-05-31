package core.backend.wrongProblem.wrongWorkbook.service;

import core.backend.member.service.WrongFigureService;
import core.backend.problem.question.domain.Question;
import core.backend.problem.question.domain.QuestionData;
import core.backend.problem.question.service.QuestionService;
import core.backend.problem.workbook.domain.Workbook;
import core.backend.problem.workbook.service.WorkbookService;
import core.backend.wrongProblem.wrongQuestion.domain.WrongQuestion;
import core.backend.wrongProblem.wrongQuestion.service.WrongQuestionService;
import core.backend.wrongProblem.wrongWorkbook.domain.WrongWorkbook;
import core.backend.wrongProblem.wrongWorkbook.dto.WrongWorkbookRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WrongWorkbookFacadeImpl implements WrongWorkbookFacade {

    private final WrongWorkbookService wrongWorkbookService;
    private final WorkbookService workbookService;
    private final QuestionService questionService;
    private final WrongQuestionService wrongQuestionService;
    private final WrongFigureService wrongFigureService;

    @Override
    @Transactional
    public WrongWorkbook saveWrongWorkbookAndQuestion(Long memberId, WrongWorkbookRequestDto dto) {
        WrongWorkbook wrongWorkbook = saveAndGetWrongWorkbook(memberId, dto);
        for (QuestionData questionData : getQuestionDataList(dto)) {
            updateWrongFigureAndSaveWrongQuestion(questionData, wrongWorkbook);
        }
        return wrongWorkbook;
    }

    private WrongWorkbook saveAndGetWrongWorkbook(Long memberId, WrongWorkbookRequestDto dto) {
        Workbook workbook = workbookService.findByIdOrThrow(dto.getId());
        Long id = wrongWorkbookService.save(
                WrongWorkbook.builder()
                        .workbook(dto.toWorkbookData(workbook))
                        .memberId(memberId)
                        .build());
        return wrongWorkbookService.findByIdOrThrow(id);
    }

    private List<QuestionData> getQuestionDataList(WrongWorkbookRequestDto dto) {
        List<Question> questionList = dto.getQuestionIdList()
                .stream()
                .map(questionService::findByIdOrThrow)
                .collect(Collectors.toList());

        return questionList.stream()
                .map(QuestionData::new)
                .collect(Collectors.toList());
    }

    private void updateWrongFigureAndSaveWrongQuestion(QuestionData questionData, WrongWorkbook wrongWorkbook) {
        wrongFigureService.update(wrongWorkbook.getMemberId(), questionData.getCategory());
        wrongQuestionService.save(
                WrongQuestion.builder()
                        .question(questionData)
                        .wrongWorkbook(wrongWorkbook)
                        .build()
        );
    }
}
