package com.javamentor.qa.platform.webapp.converters;

import com.javamentor.qa.platform.models.dto.CreateSingleChatDto;
import com.javamentor.qa.platform.models.entity.chat.SingleChat;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import com.javamentor.qa.platform.webapp.controllers.exceptions.UserNotFoundException;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Mapper(componentModel = "spring")
@Component
public abstract class SingleChatConverter  {

    @Autowired
    private UserService userService;

    public static SingleChatConverter INSTANCE = Mappers.getMapper(SingleChatConverter.class);


    @Mapping(source = "createSingleChatDto.userId", target = "useTwo", qualifiedByName = "getUserTwo")
    public abstract SingleChat createSingleChatDtoToSingleChat(CreateSingleChatDto createSingleChatDto);

    @Named("getUserTwo")
    User getUserById(Long id) {
        return userService.getById(id)
                .orElseThrow(() -> new UserNotFoundException("не найден  Юзер с id: " + id));
    }

}
