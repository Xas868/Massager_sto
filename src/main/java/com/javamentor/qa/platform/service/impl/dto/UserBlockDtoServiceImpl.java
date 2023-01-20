package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.UserBlockDtoDao;
import com.javamentor.qa.platform.models.dto.UserBlockDto;
import com.javamentor.qa.platform.service.abstracts.dto.UserBlockDtoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserBlockDtoServiceImpl implements UserBlockDtoService {

    private final UserBlockDtoDao userBlockDtoDao;

    public UserBlockDtoServiceImpl(UserBlockDtoDao userBlockDtoDao){
        this.userBlockDtoDao = userBlockDtoDao;
    }

    public List<UserBlockDto> getAllBlockedUsers(Long id){
        return userBlockDtoDao.getAllBlockedUsers(id);
    }
}
