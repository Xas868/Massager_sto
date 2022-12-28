package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.UserProfileTagDtoDao;
import com.javamentor.qa.platform.models.dto.UserProfileTagDto;
import com.javamentor.qa.platform.service.abstracts.dto.UserProfileTagDtoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProfileTagDtoServiceImpl implements UserProfileTagDtoService {
    private final UserProfileTagDtoDao userProfileTagDtoDao;

    public UserProfileTagDtoServiceImpl(UserProfileTagDtoDao userProfileTagDtoDao) {
        this.userProfileTagDtoDao = userProfileTagDtoDao;
    }

    public List<UserProfileTagDto> getAllUserProfileTagDtoByUserId(Long id) {
        return userProfileTagDtoDao.getAllUserProfileTagDtoByUserId(id);
    }
}