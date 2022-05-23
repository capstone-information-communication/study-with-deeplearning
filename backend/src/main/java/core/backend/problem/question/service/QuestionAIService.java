package core.backend.problem.question.service;

import core.backend.problem.choice.domain.Choice;
import core.backend.problem.choice.dto.ChoiceAIResponseDto;
import core.backend.problem.choice.service.ChoiceService;
import core.backend.problem.question.domain.Question;
import core.backend.problem.question.dto.QuestionAIRequestDto;
import core.backend.problem.question.exception.QuestionFlaskResponseException;
import core.backend.problem.workbook.service.WorkbookService;
import core.backend.problem.question.dto.QuestionAIResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionAIService {

    private final QuestionService questionService;
    private final WorkbookService workbookService;
    private final ChoiceService choiceService;
    private final RestTemplate restTemplate;

    @Value("${flask.url}")
    private String url;

    public void requestTextAndSaveResponse(Long workbookId, String text) {
        getQuestionSaveResponseDto(text)
                .forEach(questionDto -> saveQuestionAndChoices(workbookId, questionDto));
    }

    private List<QuestionAIResponseDto> getQuestionSaveResponseDto(String text) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return Arrays.asList(
                Optional.ofNullable(
                        restTemplate.postForObject(
                                url,
                                new HttpEntity<>(new QuestionAIRequestDto(text), headers),
                                QuestionAIResponseDto[].class)
                ).orElseThrow(QuestionFlaskResponseException::new));
    }

    private void saveQuestionAndChoices(Long workbookId, QuestionAIResponseDto questionDto) {
        Question question = questionDto.toEntity(
                workbookService.findByIdOrThrow(workbookId));
        questionDto.getChoiceList()
                .forEach(choiceDto -> saveChoice(
                        questionService.save(question),
                        choiceDto));
    }

    private void saveChoice(Long questionId, ChoiceAIResponseDto dto) {
        Choice choice = dto.toEntity(
                questionService.findByIdOrThrow(questionId));
        choiceService.save(choice);
    }
}
