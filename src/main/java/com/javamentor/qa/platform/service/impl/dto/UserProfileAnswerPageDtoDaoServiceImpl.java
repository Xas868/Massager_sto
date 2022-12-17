package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;
import com.javamentor.qa.platform.models.dto.UserProfileAnswerDto;
import com.javamentor.qa.platform.service.abstracts.dto.UserProfileAnswerPageDtoDaoService;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public class UserProfileAnswerPageDtoDaoServiceImpl extends DtoServiceImpl<UserProfileAnswerDto> implements UserProfileAnswerPageDtoDaoService {
    public UserProfileAnswerPageDtoDaoServiceImpl(Map<String, PageDtoDao<UserProfileAnswerDto>> stringPageDtoDaoMap) {
        super(stringPageDtoDaoMap);
    }
}
