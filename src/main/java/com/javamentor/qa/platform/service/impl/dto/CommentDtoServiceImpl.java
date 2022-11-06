package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;
import com.javamentor.qa.platform.models.dto.question.QuestionCommentDto;
import com.javamentor.qa.platform.service.abstracts.dto.CommentDtoService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CommentDtoServiceImpl extends DtoServiceImpl<QuestionCommentDto> implements CommentDtoService {

    public CommentDtoServiceImpl(Map<String, PageDtoDao<QuestionCommentDto>> stringPageDtoDaoMap) {
        super(stringPageDtoDaoMap);
    }

}

