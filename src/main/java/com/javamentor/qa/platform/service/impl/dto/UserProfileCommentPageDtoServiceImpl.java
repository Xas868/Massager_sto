package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;
import com.javamentor.qa.platform.models.dto.UserProfileCommentDto;
import com.javamentor.qa.platform.service.abstracts.dto.UserProfileCommentPageDtoService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserProfileCommentPageDtoServiceImpl
        extends DtoServiceImpl<UserProfileCommentDto>
        implements UserProfileCommentPageDtoService {

    public UserProfileCommentPageDtoServiceImpl(Map<String, PageDtoDao<UserProfileCommentDto>> stringPageDtoDaoMap) {
        super(stringPageDtoDaoMap);
    }
}
