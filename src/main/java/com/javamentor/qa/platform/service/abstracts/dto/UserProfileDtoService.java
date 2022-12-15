package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.UserProfileAnswerDto;
import com.javamentor.qa.platform.models.entity.question.answer.ProfileAnswerSort;

import java.util.List;

public interface UserProfileDtoService extends PageDtoService<UserProfileAnswerDto> {
    List<UserProfileAnswerDto> getAllUserProfileAnswerDtoByIdAndSort(Long id, ProfileAnswerSort profileAnswerSort);
}