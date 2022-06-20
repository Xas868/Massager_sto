package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.GroupChatDto;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;

import java.util.Optional;

public interface ChatDtoService {
    Optional<GroupChatDto> getGroupChatDtoById(long chatId, PaginationData properties);
}
