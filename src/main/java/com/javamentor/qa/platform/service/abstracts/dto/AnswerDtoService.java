package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.AnswerDTO;

import java.util.List;
import java.util.Optional;

public interface AnswerDtoService extends PageDtoService<AnswerDTO> {
    Optional<AnswerDTO> getUndeletedAnswerDtoById(Long id);

    List<AnswerDTO> getAllUndeletedAnswerDtoByQuestionId(Long questionId);
}
