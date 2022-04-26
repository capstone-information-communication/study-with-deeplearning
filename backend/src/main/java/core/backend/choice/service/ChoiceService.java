package core.backend.choice.service;

import core.backend.choice.domain.Choice;
import core.backend.choice.dto.ChoiceUpdateRequestDto;
import core.backend.choice.exception.ChoiceNotFoundException;
import core.backend.choice.repository.ChoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChoiceService {

    private final ChoiceRepository choiceRepository;

    @Transactional
    public Long save(Choice choice) {
        choiceRepository.save(choice);
        return choice.getId();
    }

    @Transactional
    public Choice update(Long id, ChoiceUpdateRequestDto dto) {
        Choice choice = findByIdOrThrow(id);
        choice.update(dto);
        return choice;
    }

    @Transactional
    public void deleteById(Long id) {
        choiceRepository.deleteById(id);
    }

    public Choice findByIdOrThrow(Long id) {
        return choiceRepository.findById(id)
                .orElseThrow(ChoiceNotFoundException::new);
    }

    public Page<Choice> findAll(Pageable pageable) {
        return choiceRepository.findAll(pageable);
    }
}
