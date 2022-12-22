package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.UserProfileTagDto;

import java.util.List;

public interface UserProfileTagDtoDao {
    List<UserProfileTagDto> getAllUserProfileTagDtoByUserId(Long id);
}