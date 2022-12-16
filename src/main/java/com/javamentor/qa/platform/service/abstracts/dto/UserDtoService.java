package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.UserDto;
import com.javamentor.qa.platform.models.dto.UserProfileQuestionDto;
import com.javamentor.qa.platform.models.dto.UserProfileTagDto;
import com.javamentor.qa.platform.models.entity.question.ProfileQuestionSort;
import com.javamentor.qa.platform.models.util.CalendarPeriod;

import java.util.List;
import java.util.Optional;

public interface UserDtoService extends PageDtoService<UserDto> {
    Optional<UserDto> findUserDtoById(Long id);
    List<UserProfileQuestionDto> getAllUserProfileQuestionDtoById(Long id);
    List<UserProfileQuestionDto> getUserProfileQuestionDtoByUserIdIsDeleted(Long id);
    List<UserDto> getTopUsersForDaysRankedByNumberOfQuestions(CalendarPeriod calendarPeriod);
    Long getCountAnswersPerWeekByUserId (Long userId);

    List<Long> getUnregisteredUserIds(List<Long> userIds);

    List<UserProfileQuestionDto> getAllUserProfileQuestionDtoByIdAndSort(Long id, ProfileQuestionSort profileQuestionSort);

    List<UserProfileTagDto> getUserProfileTagDto(Long id);
}
