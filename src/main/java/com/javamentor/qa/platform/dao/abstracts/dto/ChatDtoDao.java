package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.ChatUserDto;
import com.javamentor.qa.platform.models.dto.GroupChatDto;
import com.javamentor.qa.platform.models.dto.SingleChatDto;

import java.util.List;
import java.util.Optional;

public interface ChatDtoDao {
    Optional<GroupChatDto> getGroupChatDto(long chatId);

    List<SingleChatDto> getAllSingleChatDtoByUserId(Long userId);

    List<ChatUserDto> getChatUsersDtoByChatId(Long chatId);

    public Boolean checkUpUserIsMemberOfGroupChat(long id, long chatId);

}
