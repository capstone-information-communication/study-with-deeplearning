package core.backend.wrongProblem.wrongAnswer.service;

import core.backend.likeWorkbook.exception.LikeWorkbookNotRegisterException;
import core.backend.problem.question.service.QuestionService;
import core.backend.problem.workbook.service.WorkbookService;
import core.backend.wrongProblem.wrongAnswer.domain.WrongAnswer;
import core.backend.wrongProblem.wrongAnswer.dto.WrongAnswerInfoResponseDto;
import core.backend.wrongProblem.wrongAnswer.dto.WrongAnswerItem;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
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
        HashMap<Long, List<WrongAnswerItem>> wrongAnswerMap = getWrongAnswerMap(
                wrongAnswerService.findByMemberId(memberId, pageable));

        return wrongAnswerMap.entrySet()
                .stream()
                .map(item -> getWrongAnswerInfoResponseDto(item.getKey(), item.getValue()))
                .collect(Collectors.toList());
    }

    private HashMap<Long, List<WrongAnswerItem>> getWrongAnswerMap(Page<WrongAnswer> wrongAnswerPageList) {
        HashMap<Long, List<WrongAnswerItem>> wrongAnswerMap = new HashMap<>();
        for (WrongAnswer wrongAnswer : wrongAnswerPageList) {
            setWrongAnswerMap(wrongAnswerMap,
                    wrongAnswer.getWorkbookId(),
                    new WrongAnswerItem(wrongAnswer.getId(), questionService.findByIdOrThrow(wrongAnswer.getQuestionId())));
        }
        return wrongAnswerMap;
    }

    private WrongAnswerInfoResponseDto getWrongAnswerInfoResponseDto(Long key, List<WrongAnswerItem> value) {
        return new WrongAnswerInfoResponseDto(
                workbookService.findByIdOrThrow(key), value);
    }

    private void setWrongAnswerMap(HashMap<Long, List<WrongAnswerItem>> map, Long key, WrongAnswerItem value) {
        if (!map.containsKey(key)) {
            map.put(key, new ArrayList<WrongAnswerItem>());
        }
        map.get(key).add(value);
    }

}
