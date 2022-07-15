package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.ReadWriteDao;
import com.javamentor.qa.platform.models.dto.SingleChatDto;
import com.javamentor.qa.platform.models.entity.chat.Message;
import com.javamentor.qa.platform.models.entity.chat.SingleChat;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.SingleChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class SingleChatRoomServiceImpl extends ReadWriteServiceImpl<SingleChat, Long> implements SingleChatRoomService {
    @Autowired
    private final MessageServiceImpl messageService;


    public SingleChatRoomServiceImpl(@Qualifier("singleChatRoomDaoImpl") ReadWriteDao<SingleChat, Long> readWriteDao, MessageServiceImpl messageService) {
        super(readWriteDao);
        this.messageService = messageService;
    }
    @Override
    public SingleChatDto createSingleChatDto(User currentUser, User destinationUser, String firstMessage) {
        SingleChat singleChat = SingleChat.builder()
                .userOne(currentUser)
                .useTwo(destinationUser)
                .build();
        Message message = Message.builder()
                .message(firstMessage)
                .userSender(currentUser)
                .build();
        SingleChatDto singleChatDto = new SingleChatDto();
        singleChatDto.setLastMessage(message.getMessage());
        this.persist(singleChat);
        messageService.persist(message);
        return singleChatDto;
    }
}
