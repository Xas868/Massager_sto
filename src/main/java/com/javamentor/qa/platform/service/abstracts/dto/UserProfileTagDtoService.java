package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.UserProfileTagDto;

import java.util.List;
public interface UserProfileTagDtoService {
    List<UserProfileTagDto> getAllUserProfileTagDtoByUserId(Long id);
}
