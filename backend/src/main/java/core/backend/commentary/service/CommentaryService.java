package core.backend.commentary.service;

import core.backend.commentary.domain.Commentary;
import core.backend.commentary.dto.CommentaryUpdateRequestDto;
import core.backend.commentary.exception.CommentaryNotFoundException;
import core.backend.commentary.repository.CommentaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentaryService {

    private final CommentaryRepository commentaryRepository;

    @Transactional
    public Long save(Commentary commentary) {
        commentaryRepository.save(commentary);
        return commentary.getId();
    }

    @Transactional
    public Commentary update(Long id, CommentaryUpdateRequestDto dto) {
        Commentary commentary = findByIdOrThrow(id);
        commentary.update(dto);
        return commentary;
    }

    @Transactional
    public void deleteById(Long id) {
        commentaryRepository.deleteById(id);
    }

    public Commentary findByIdOrThrow(Long id) {
        return commentaryRepository.findById(id)
                .orElseThrow(CommentaryNotFoundException::new);
    }

    public Page<Commentary> findAll(Pageable pageable) {
        return commentaryRepository.findAll(pageable);
    }
}
