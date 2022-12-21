package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.UserProfileTagDto;

import java.util.List;
import java.util.Optional;

public interface UserProfileTagDtoDaoService {
    Optional<List<UserProfileTagDto>> getAllUserProfileTagDtoByUserId(Long id);
}
