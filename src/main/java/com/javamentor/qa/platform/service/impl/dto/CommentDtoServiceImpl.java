package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;
import com.javamentor.qa.platform.models.dto.AnswerCommentDto;
import com.javamentor.qa.platform.service.abstracts.dto.CommentDtoService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CommentDtoServiceImpl extends DtoServiceImpl<AnswerCommentDto> implements CommentDtoService {

    public CommentDtoServiceImpl(Map<String, PageDtoDao<AnswerCommentDto>> stringPageDtoDaoMap) {
        super(stringPageDtoDaoMap);
    }
}
