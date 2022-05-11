package core.backend.question.service;

import core.backend.question.domain.Question;
import core.backend.question.dto.QuestionSaveFlaskRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionFacade {

    private final QuestionService questionService;
    private final RestTemplate restTemplate;

    @Value("flask.url")
    private String flaskUrl;

    public List<Question> getQuestionListUsingNER(Long workbookId, String text) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        /* TODO:
            * response를 받아 toEntity를 사용하여 Question 객체로 만들어준다.
            * 만든 객체를 대상으로 저장을 실행한다.
            * 이떄 choice, question을 입력 받을 수 있어야한다.
            * 그에 맞는 response를 만들어야한다.
        */
        Question[] questions = restTemplate.postForObject(
                flaskUrl,
                new QuestionSaveFlaskRequestDto(text),
                Question[].class);

        return saveQuestionList(Arrays.asList(questions)).stream()
                .map(id -> questionService.findByIdOrThrow(id))
                .collect(Collectors.toList());
    }

    private List<Long> saveQuestionList(List<Question> questionList) {
        return questionList.stream()
                .map(question -> questionService.save(question))
                .collect(Collectors.toList());
    }
}
