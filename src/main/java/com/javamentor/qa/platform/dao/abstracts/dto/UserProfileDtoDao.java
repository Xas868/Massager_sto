package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.UserProfileAnswerDto;

import java.util.List;

public interface UserProfileDtoDao {
    List<UserProfileAnswerDto> getAllUserProfileAnswerDtoById(Long id);
}
