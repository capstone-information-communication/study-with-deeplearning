package core.backend.global.error.exception;

import core.backend.wrongAnswer.exception.WrongAnswerNotFound;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum ErrorCode {
    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    WRONG_ANSWER_NOT_FOUND(NOT_FOUND, "오답 문제를 찾을 수 없습니다", WrongAnswerNotFound.class),
    TOTAL_NOT_FOUND(NOT_FOUND, "합계를 가져올 수 없습니다", TotalNotFound.class),

    /* 404 NOT_FOUND : 에러 클래스를 찾을 수 없는 경우 */
    CLASS_NOT_FOUND(NOT_FOUND, "에러 클래스를 찾을 수 없습니다", NotFoundClassException.class),
    ;

    private final HttpStatus httpStatus;
    private final String message;
    private final Class<? extends Exception> klass;

    ErrorCode(HttpStatus httpStatus, String message, Class<? extends Exception> klass) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.klass = klass;
    }

    public static ErrorCode findByClass(Class<? extends Exception> klass) {
        return Arrays.stream(ErrorCode.values())
                .filter(code -> code.klass.equals(klass))
                .findAny()
                .orElseThrow(NotFoundClassException::new);
    }
}
