package com.javamentor.qa.platform.webapp.converters;

import com.javamentor.qa.platform.models.dto.GroupChatDto;
import com.javamentor.qa.platform.models.entity.chat.Chat;
import com.javamentor.qa.platform.models.entity.chat.ChatType;
import com.javamentor.qa.platform.models.entity.chat.GroupChat;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Mapper(componentModel = "spring")
@Component
public abstract class GroupChatConverter {
    @Autowired
    private UserService userService;

    @Mappings(value = {
            @Mapping(target = "chat", expression = "java(getChat(groupChatDto))"),
            @Mapping(target = "users", expression = "java(idToUsers(userIds))")
    })
    public abstract GroupChat groupChatDTOToGroupChat(GroupChatDto groupChatDto, List<Long> userIds);

    HashSet<User> idToUsers(List<Long> userIds) {
        if (!userIds.isEmpty()){
            return new HashSet<>(userService.getAllByIds(userIds));
        }
        return new HashSet<>();
    }

    Chat getChat(GroupChatDto groupChatDto) {
        return Chat.builder()
                .chatType(ChatType.GROUP)
                .title(groupChatDto.getChatName())
                .image(groupChatDto.getImage())
                .build();
    }
}
