package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;
import com.javamentor.qa.platform.models.dto.UserProfileAnswerDto;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public class UserProfileAnswerPageDtoDaoServiceImpl extends DtoServiceImpl<UserProfileAnswerDto>{
    public UserProfileAnswerPageDtoDaoServiceImpl(Map<String, PageDtoDao<UserProfileAnswerDto>> stringPageDtoDaoMap) {
        super(stringPageDtoDaoMap);
    }
}
