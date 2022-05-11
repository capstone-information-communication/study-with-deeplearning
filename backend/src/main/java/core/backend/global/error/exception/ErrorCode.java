package core.backend.global.error.exception;

import core.backend.choice.exception.ChoiceNotFoundException;
import core.backend.likeWorkbook.exception.ExistLikeWorkbookException;
import core.backend.likeWorkbook.exception.LikeWorkbookNotFoundException;
import core.backend.likeWorkbook.exception.LikeWorkbookNotRegisterException;
import core.backend.member.exception.*;
import core.backend.question.exception.QuestionFlaskResponseException;
import core.backend.question.exception.QuestionNotFoundException;
import core.backend.workbook.exception.WorkbookExistTitleException;
import core.backend.workbook.exception.WorkbookNotAuthorException;
import core.backend.workbook.exception.WorkbookNotFoundException;
import core.backend.wrongAnswer.exception.WrongAnswerNotFoundException;
import core.backend.wrongAnswer.exception.WrongAnswerNotRegisterException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
public enum ErrorCode {
    /* 400 BAD_REQUEST : 잘못된 요청 */
    WORKBOOK_EXIST_TITLE_BAD_REQUEST(BAD_REQUEST, "이미 존재하는 제목입니다", WorkbookExistTitleException.class),
    WORKBOOK_NOT_AUTHOR_BAD_REQUEST(BAD_REQUEST, "문제집 작성자가 아닙니다", WorkbookNotAuthorException.class),
    EXPIRED_TOKEN(BAD_REQUEST, "만료된 토큰입니다", ExpiredTokenException.class),
    INVALID_TOKEN(BAD_REQUEST, "옳바르지 않은 형식의 토큰입니다", InvalidTokenException.class),
    WRONG_TOKEN(BAD_REQUEST, "잘못된 토큰입니다", WrongTokenException.class),
    EXIST_EMAIL(BAD_REQUEST, "이미 존재하는 이메일입니다", EmailExistException.class),
    EXIST_NICKNAME(BAD_REQUEST, "이미 존재하는 닉네임입니다", NickNameExistException.class),
    SIGN_IN_FAILED(BAD_REQUEST, "이메일 혹은 비밀번호를 확인해주시기 바랍니다", SignInFailedException.class),
    NOT_ADMIN(BAD_REQUEST, "어드민만 접속할 수 있습니다", NotAdminException.class),
    LIKE_WORKBOOK_NOT_REGISTER(BAD_REQUEST, "자신의 좋아요 문제집만 삭제할 수 있습니다", LikeWorkbookNotRegisterException.class),
    EXIST_LIKE_WORKBOOK(BAD_REQUEST, "이미 등록한 좋아요 문제집입니다", ExistLikeWorkbookException.class),
    WRONG_ANSWER_NOT_REGISTER(BAD_REQUEST, "자신의 오답 문제집만 삭제할 수 있습니다", WrongAnswerNotRegisterException.class),
    FLASK_RESPONSE_EXCEPTION(BAD_REQUEST, "플라스크 서버의 응답이 null 입니다", QuestionFlaskResponseException.class),

    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    MEMBER_NOT_FOUND(NOT_FOUND, "회원을 찾을 수 없습니다", MemberNotFoundException.class),
    WORKBOOK_NOT_FOUND(NOT_FOUND, "문제집을 찾을 수 없습니다", WorkbookNotFoundException.class),
    WRONG_ANSWER_NOT_FOUND(NOT_FOUND, "오답 문제를 찾을 수 없습니다", WrongAnswerNotFoundException.class),
    TOTAL_NOT_FOUND(NOT_FOUND, "합계 퀴리 결과를 가져올 수 없습니다", TotalNotFound.class),
    CLASS_NOT_FOUND(NOT_FOUND, "에러 클래스를 찾을 수 없습니다", NotFoundClassException.class),
    QUESTION_NOT_FOUND(NOT_FOUND, "문제를 찾을 수 없습니다", QuestionNotFoundException.class),
    CHOICE_NOT_FOUND(NOT_FOUND, "선택지를 찾을 수 없습니다", ChoiceNotFoundException.class),
    LIKE_WORKBOOK_NOT_FOUND(NOT_FOUND, "좋아요 문제집을 찾을 수 없습니다", LikeWorkbookNotFoundException.class),
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
