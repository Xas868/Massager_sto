package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.ReadWriteDao;
import com.javamentor.qa.platform.models.dto.SingleChatDto;
import com.javamentor.qa.platform.models.entity.chat.Message;
import com.javamentor.qa.platform.models.entity.chat.SingleChat;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.SingleChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SingleChatRoomServiceImpl extends ReadWriteServiceImpl<SingleChat, Long> implements SingleChatRoomService {
    @Autowired
    private final MessageServiceImpl messageService;


    public SingleChatRoomServiceImpl(ReadWriteDao<SingleChat, Long> readWriteDao, MessageServiceImpl messageService) {
        super(readWriteDao);
        this.messageService = messageService;
    }
    @Override
    public SingleChatDto createSingleChatDto(User currentUser, User destinationUser, String firstMessage) {
        SingleChat singleChat = new SingleChat();
        Message message = new Message();
        message.setMessage(firstMessage);
        message.setChat(singleChat.getChat());
        message.setUserSender(currentUser);
        singleChat.setUserOne(currentUser);
        singleChat.setUseTwo(destinationUser);
        singleChat.setChat(singleChat.getChat());
        SingleChatDto singleChatDto = new SingleChatDto();
        singleChatDto.setLastMessage(message.getMessage());
        this.persist(singleChat);
        messageService.persist(message);
        return singleChatDto;
    }
}
