package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.UserProfileTagDtoDao;
import com.javamentor.qa.platform.models.dto.UserProfileTagDto;
import com.javamentor.qa.platform.service.abstracts.dto.UserProfileTagDtoDaoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserProfileTagDtoDaoServiceImpl implements UserProfileTagDtoDaoService {
    private final UserProfileTagDtoDao userProfileTagDtoDao;

    public UserProfileTagDtoDaoServiceImpl(UserProfileTagDtoDao userProfileTagDtoDao) {
        this.userProfileTagDtoDao = userProfileTagDtoDao;
    }

    @Override
    public Optional<List<UserProfileTagDto>> getAllUserProfileTagDtoByUserId(Long id) {
        return userProfileTagDtoDao.getAllUserProfileTagDtoByUserId(id);
    }
}
