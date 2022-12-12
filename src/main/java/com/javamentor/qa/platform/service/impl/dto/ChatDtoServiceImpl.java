package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.ChatDtoDao;
import com.javamentor.qa.platform.dao.abstracts.model.GroupChatRoomDao;
import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;
import com.javamentor.qa.platform.exception.GroupChatException;
import com.javamentor.qa.platform.models.dto.ChatDto;
import com.javamentor.qa.platform.models.dto.ChatUserDto;
import com.javamentor.qa.platform.models.dto.SingleChatDto;
import com.javamentor.qa.platform.models.dto.GroupChatDto;
import com.javamentor.qa.platform.models.entity.chat.GroupChat;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.ChatDtoService;
import com.javamentor.qa.platform.service.abstracts.dto.MessageDtoService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ChatDtoServiceImpl extends DtoServiceImpl<ChatDto> implements ChatDtoService {

    private final ChatDtoDao chatDtoDao;
    private final GroupChatRoomDao groupChatRoomDao;

    private final MessageDtoService messageDtoService;

    public ChatDtoServiceImpl(Map<String, PageDtoDao<ChatDto>> daoMap, ChatDtoDao chatDtoDao, GroupChatRoomDao groupChatRoomDao, MessageDtoService messageDtoService) {
        super(daoMap);
        this.chatDtoDao = chatDtoDao;
        this.groupChatRoomDao = groupChatRoomDao;
        this.messageDtoService = messageDtoService;
    }

    @Override
    public List<SingleChatDto> getAllSingleChatDtoByUserId(Long userId) {
        return chatDtoDao.getAllSingleChatDtoByUserId(userId);

    }

    @Override
    @Transactional
    public List<ChatUserDto> getChatUsersDtoByChatId(Long chatId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<GroupChat> groupChatOptional = groupChatRoomDao.getById(chatId);
        if (!groupChatOptional.isPresent()) {
            throw new GroupChatException("The group chat doesn`t exist. Sorry boy");
        }
        GroupChat groupChat = groupChatOptional.get();
        if (!chatDtoDao.isExistUserOfGroupChat(user.getId(), groupChat.getId())) {
            throw new GroupChatException("You aren`t member of group chat. The information for groupchat`s members . Sorry boy");
        }
        return chatDtoDao.getChatUsersDtoByChatId(chatId);
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
}
