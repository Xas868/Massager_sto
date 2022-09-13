package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.ChatDtoDao;
import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;
import com.javamentor.qa.platform.models.dto.*;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import com.javamentor.qa.platform.service.abstracts.dto.ChatDtoService;
import com.javamentor.qa.platform.service.abstracts.dto.MessageDtoService;
import com.javamentor.qa.platform.service.util.Comparators;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChatDtoServiceImpl extends DtoServiceImpl<ChatDto> implements ChatDtoService {

    private final ChatDtoDao chatDtoDao;
    private final MessageDtoService messageDtoService;

    public ChatDtoServiceImpl(Map<String, PageDtoDao<ChatDto>> daoMap, ChatDtoDao chatDtoDao, MessageDtoService messageDtoService) {
        super(daoMap);
        this.chatDtoDao = chatDtoDao;
        this.messageDtoService = messageDtoService;
    }

    @Override
    public List<SingleChatDto> getAllSingleChatDtoByUserId(Long userId) {
        return chatDtoDao.getAllSingleChatDtoByUserId(userId);

    }

    @Override
    public Optional<GroupChatDto> getGroupChatDtoById(long chatId, PaginationData properties) {
        Optional<GroupChatDto> groupChatDtoOptional = chatDtoDao.getGroupChatDto(chatId);
        if (groupChatDtoOptional.isEmpty()) {
            return Optional.empty();
        } else {
            GroupChatDto groupChatDto = groupChatDtoOptional.get();
            groupChatDto.setMessages(messageDtoService.getPageDto(properties));
            return Optional.of(groupChatDto);
        }
    }

    @Override
    public List<ChatDto> getAllChatsByNameAndUserId(String chatName, Long userId) {
        return chatDtoDao.getAllChatsByNameAndUserId(chatName, userId)
                .stream()
                .sorted(Comparators::isPinAndLastMessageDateComparator)
                .collect(Collectors.toList());
    }
}
