package core.backend.wrongProblem.wrongWorkbook.service;

import core.backend.wrongProblem.wrongWorkbook.domain.WrongWorkbook;
import core.backend.wrongProblem.wrongWorkbook.dto.WrongWorkbookRequestDto;

public interface WrongWorkbookFacade {
    WrongWorkbook saveWrongWorkbookAndQuestion(Long memberId, WrongWorkbookRequestDto dto);
}
