package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.MessageDto;
import com.javamentor.qa.platform.models.dto.PageDTO;
import com.javamentor.qa.platform.models.dto.question.QuestionCommentDto;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;

public interface CommentDtoService extends PageDtoService<QuestionCommentDto> {

    PageDTO<QuestionCommentDto> getPageDto(PaginationData properties);
}
