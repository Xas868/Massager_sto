package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.UserDto;
import com.javamentor.qa.platform.models.dto.UserProfileQuestionDto;

import java.util.List;
import java.util.Optional;

public interface UserDtoDao {
    Optional<UserDto> findUserDto(Long id);

    List<UserProfileQuestionDto> getAllUserProfileQuestionDtoByUserIdWhereQuestionIsDeleted(Long id);
    List<UserProfileQuestionDto> getAllUserProfileQuestionDtoById(Long id);
    List<UserDto> getTopUsersForDaysRankedByNumberOfQuestions(Integer daysCount, Integer userCount);
    Long getCountAnswersPerWeekByUserId (Long userId);
}
