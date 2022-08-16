package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.AnswerCommentDto;
import com.javamentor.qa.platform.models.dto.AnswerDTO;
import com.javamentor.qa.platform.models.dto.AnswerUserDto;

import java.util.List;
import java.util.Optional;

public interface AnswerDtoDao {

    Optional<AnswerDTO> getUndeletedAnswerDtoById(Long id);

    List<AnswerDTO> getAllUndeletedAnswerDtoByQuestionId(Long questionId);

    List<AnswerCommentDto> getAllCommentsDtoByAnswerId(Long answerId);

    List<AnswerUserDto> getLastAnswersForWeek(Long userId);
}
