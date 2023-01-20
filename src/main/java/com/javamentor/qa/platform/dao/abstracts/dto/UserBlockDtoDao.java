package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.UserBlockDto;

import java.util.List;

public interface UserBlockDtoDao {

    List<UserBlockDto> getAllBlockedUsers(Long id);
}
