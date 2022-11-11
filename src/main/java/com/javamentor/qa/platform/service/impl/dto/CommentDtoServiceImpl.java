package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;
import com.javamentor.qa.platform.models.dto.CommentDto;
import com.javamentor.qa.platform.service.abstracts.dto.CommentDtoService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CommentDtoServiceImpl extends DtoServiceImpl<CommentDto> implements CommentDtoService {

    public CommentDtoServiceImpl(Map<String, PageDtoDao<CommentDto>> stringPageDtoDaoMap) {
        super(stringPageDtoDaoMap);
    }
}
