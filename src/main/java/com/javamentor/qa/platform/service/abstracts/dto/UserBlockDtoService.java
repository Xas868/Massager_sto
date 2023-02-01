package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.UserBlockDto;

import java.util.List;

public interface UserBlockDtoService {

    List<UserBlockDto> getAllBlockedUsers(Long id);
}
