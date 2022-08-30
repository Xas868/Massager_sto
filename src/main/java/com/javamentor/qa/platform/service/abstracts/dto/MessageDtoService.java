package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.MessageDto;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;

public interface MessageDtoService {
    MessageDto getMessageDto(PaginationData properties);
}
