package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.*;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;

import java.util.Optional;
import java.util.List;

public interface ChatDtoService extends PageDtoService<ChatDto> {

    Optional<GroupChatDto> getGroupChatDtoById(long chatId, PaginationData properties);
    List<SingleChatDto> getAllSingleChatDtoByUserId(Long userId);
    List<ChatDto> getAllChatsByNameAndUserId(String name, Long userId);
}
