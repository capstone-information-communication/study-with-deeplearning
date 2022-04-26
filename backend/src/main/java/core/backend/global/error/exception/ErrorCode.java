package core.backend.global.error.exception;

import core.backend.member.exception.ExpiredTokenException;
import core.backend.member.exception.InvalidTokenException;
import core.backend.member.exception.MemberNotFound;
import core.backend.member.exception.WrongTokenException;
import core.backend.workbook.exception.WorkbookExistTitleException;
import core.backend.workbook.exception.WorkbookNotAuthorException;
import core.backend.wrongAnswer.exception.WrongAnswerNotFound;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum ErrorCode {
    /* 400 BAD_REQUEST : 잘못된 요청 */
    WORKBOOK_EXIST_TITLE_BAD_REQUEST(BAD_REQUEST, "이미 존재하는 제목입니다", WorkbookExistTitleException.class),
    WORKBOOK_NOT_AUTHOR_BAD_REQUEST(BAD_REQUEST, "문제집 작성자가 아닙니다", WorkbookNotAuthorException.class),
    EXPIRED_TOKEN(BAD_REQUEST, "만료된 토큰입니다", ExpiredTokenException.class),
    INVALID_TOKEN(BAD_REQUEST, "옳바르지 않은 형식의 토큰입니다", InvalidTokenException.class),
    WRONG_TOKEN(BAD_REQUEST, "잘못된 토큰입니다", WrongTokenException.class),

    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    MEMBER_NOT_FOUND(NOT_FOUND, "회원을 찾을 수 없습니다", MemberNotFound.class),
    WORKBOOK_NOT_FOUND(NOT_FOUND, "문제집을 찾을 수 없습니다", WorkbookNotAuthorException.class),
    WRONG_ANSWER_NOT_FOUND(NOT_FOUND, "오답 문제를 찾을 수 없습니다", WrongAnswerNotFound.class),
    TOTAL_NOT_FOUND(NOT_FOUND, "합계 퀴리 결과를 가져올 수 없습니다", TotalNotFound.class),

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
