package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;
import com.javamentor.qa.platform.models.dto.UserProfileReputationDto;
import com.javamentor.qa.platform.service.abstracts.dto.UserProfileReputationPageDtoDaoService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserProfileReputationPageDtoDaoServiceImpl extends DtoServiceImpl<UserProfileReputationDto> implements UserProfileReputationPageDtoDaoService {

    public UserProfileReputationPageDtoDaoServiceImpl(Map<String, PageDtoDao<UserProfileReputationDto>> stringPageDtoDaoMap) {
        super(stringPageDtoDaoMap);
    }
}
