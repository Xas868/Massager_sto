package com.javamentor.qa.platform.webapp.converters;

import com.javamentor.qa.platform.models.dto.CreateGroupChatDto;
import com.javamentor.qa.platform.models.entity.chat.Chat;
import com.javamentor.qa.platform.models.entity.chat.ChatType;
import com.javamentor.qa.platform.models.entity.chat.GroupChat;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import com.javamentor.qa.platform.webapp.controllers.exceptions.IsEmptyUserIdsException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;

@Component
@Mapper(componentModel = "spring")
public abstract class GroupChatConverter {
    @Autowired
    private UserService userService;

    @Mappings(value = {
            @Mapping(target = "chat", expression = "java(getChat(createGroupChatDto))"),
            @Mapping(target = "users", expression = "java(idToUsers(createGroupChatDto.getUserIds()))")
    })
    public abstract GroupChat createGroupChatDTOToGroupChat(CreateGroupChatDto createGroupChatDto);

    HashSet<User> idToUsers(List<Long> userIds) throws IsEmptyUserIdsException {
        if (!userIds.isEmpty()) {
            return new HashSet<>(userService.getAllByIds(userIds));
        }
        throw new IsEmptyUserIdsException("List userIds isEmpty.");
    }

    Chat getChat(CreateGroupChatDto createGroupChatDto) {
        return Chat.builder()
                .chatType(ChatType.GROUP)
                .title(createGroupChatDto.getChatName())
                .build();
    }
}
