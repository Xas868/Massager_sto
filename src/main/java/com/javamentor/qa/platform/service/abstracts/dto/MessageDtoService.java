package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.MessageDto;
import com.javamentor.qa.platform.models.dto.PageDTO;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;

public interface MessageDtoService  extends PageDtoService<MessageDto> {
    PageDTO <MessageDto> getPageDto(PaginationData properties);
}
