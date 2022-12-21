package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.UserProfileTagDto;

import java.util.List;
import java.util.Optional;

public interface UserProfileTagDtoDao {
    Optional<List<UserProfileTagDto>> getAllUserProfileTagDtoByUserId(Long id);
}