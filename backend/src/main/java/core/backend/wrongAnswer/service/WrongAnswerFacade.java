package core.backend.wrongAnswer.service;

import core.backend.likeWorkbook.exception.LikeWorkbookNotRegisterException;
import core.backend.question.domain.Question;
import core.backend.question.service.QuestionService;
import core.backend.workbook.service.WorkbookService;
import core.backend.wrongAnswer.domain.WrongAnswer;
import core.backend.wrongAnswer.dto.WrongAnswerInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WrongAnswerFacade {

    private final WrongAnswerService wrongAnswerService;
    private final QuestionService questionService;
    private final WorkbookService workbookService;

    public void deleteByIdOrThrow(Long memberId, Long id) {
        WrongAnswer wrongAnswer = wrongAnswerService.findByIdOrThrow(id);
        isRegister(memberId, wrongAnswer.getMemberId());
        wrongAnswerService.deleteById(id);
    }

    private void isRegister(Long memberId, Long registerId) {
        if (!Objects.equals(memberId, registerId)) {
            throw new LikeWorkbookNotRegisterException();
        }
    }

    public List<WrongAnswerInfoResponseDto> findMyWrongAnswer(Long memberId, Pageable pageable) {
        HashMap<Long, List<Long>> wrongAnswerMap = getWrongAnswerMap(
                new HashMap<Long, List<Long>>(),
                wrongAnswerService.findByMemberId(memberId, pageable));

        return wrongAnswerMap.entrySet()
                .stream()
                .map(item -> getWrongAnswerInfoResponseDto(item.getKey(), item.getValue()))
                .collect(Collectors.toList());
    }

    private HashMap<Long, List<Long>> getWrongAnswerMap(HashMap<Long, List<Long>> wrongAnswerMap, Page<WrongAnswer> wrongAnswerPageList) {
        for (WrongAnswer wrongAnswer : wrongAnswerPageList) {
            setWrongAnswerMap(wrongAnswerMap,
                    wrongAnswer.getWorkbookId(),
                    wrongAnswer.getQuestionId());
        }
        return wrongAnswerMap;
    }

    private WrongAnswerInfoResponseDto getWrongAnswerInfoResponseDto(Long key, List<Long> value) {
        List<Question> questionList = value
                .stream()
                .map(questionService::findByIdOrThrow)
                .collect(Collectors.toList());

        return new WrongAnswerInfoResponseDto(
                workbookService.findByIdOrThrow(key),
                questionList);
    }

    private void setWrongAnswerMap(HashMap<Long, List<Long>> map, Long key, Long value) {
        if (!map.containsKey(key)) {
            map.put(key, new ArrayList<>());
        }
        map.get(key).add(value);
    }

}
