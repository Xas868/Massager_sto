package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.ChatDtoDao;
import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;
import com.javamentor.qa.platform.models.dto.SingleChatDto;
import com.javamentor.qa.platform.models.dto.GroupChatDto;
import com.javamentor.qa.platform.models.dto.MessageDto;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import com.javamentor.qa.platform.service.abstracts.dto.ChatDtoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ChatDtoServiceImpl extends DtoServiceImpl<MessageDto> implements ChatDtoService {

    private final ChatDtoDao chatDtoDao;

    public ChatDtoServiceImpl(Map<String, PageDtoDao<MessageDto>> daoMap, ChatDtoDao chatDtoDao) {
        super(daoMap);
        this.chatDtoDao = chatDtoDao;
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
            groupChatDto.setMessages(getPageDto(properties));
            return Optional.of(groupChatDto);
        }
    }
}
