package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.dao.impl.pagination.messagedto.MessagePageDtoByGroupChatId;
import com.javamentor.qa.platform.dao.impl.pagination.messagedto.MessagePageDtoBySingleChatId;
import com.javamentor.qa.platform.models.dto.*;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.ChatDtoService;
import com.javamentor.qa.platform.service.abstracts.model.SingleChatRoomService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import com.javamentor.qa.platform.service.impl.dto.DtoServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "ChatResourceController", description = "Позволяет работать с чатами")
@RestController
@RequestMapping("/api/user/chat")
public class ChatResourceController {
    private final DtoServiceImpl<MessageDto> messagesPaginationService;
    private final ChatDtoService chatDtoService;
    private final SingleChatRoomService singleChatRoomService;
    private final UserService userService;

    @Autowired
    private ChatResourceController(DtoServiceImpl<MessageDto> dtoService, ChatDtoService chatDtoService, SingleChatRoomService singleChatRoomService, UserService userService) {
        this.messagesPaginationService = dtoService;
        this.chatDtoService = chatDtoService;
        this.singleChatRoomService = singleChatRoomService;
        this.userService = userService;
    }

    @GetMapping("/single")
    public ResponseEntity <List<SingleChatDto>> getAllSingleChatDtoByUserId(Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        return new ResponseEntity<>(chatDtoService.getAllSingleChatDtoByUserId(currentUser.getId()), HttpStatus.OK);
    }

    @PostMapping("/single")
    public ResponseEntity<SingleChatDto> createSingleChatDtoByUserId(CreateSingleChatDto createSingleChatDto,Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        Optional<User> destinationUser = userService.getById(createSingleChatDto.getUserId());
        SingleChatDto singleChatDto = singleChatRoomService.createSingleChatDto(currentUser, destinationUser.get(), createSingleChatDto.getMessage());
        return new ResponseEntity<>(singleChatDto, HttpStatus.OK);
    }

    @Operation (summary = "Получение группового чата с сообщениями.", description = "Получение группового чата с пагинированным списком сообщений.")
    @ApiResponse (responseCode = "200", description = "Групповой чат найден", content ={
            @Content(mediaType = "application/json"),
    })
    @ApiResponse (responseCode = "400", description = "Групповой чат с указанными id не найден" , content ={
            @Content(mediaType = "application/json"),
    })
    @GetMapping("/group/{groupChatId}")
    public ResponseEntity<GroupChatDto> getGroupChatDtoById(
            @PathVariable("groupChatId")
            @Parameter(name = "Id группового чата.", required = true, description = "Id группового чата является обязательным параметром.")
                    long groupChatId,
            @RequestParam(name = "itemsOnPage", defaultValue = "10")
            @Parameter (name = "Количество сообщений на странице.",
                    description = "Необязательный параметр. Позволяет настроить количество сообщений на одной странице. По-умолчанию равен 10.")
                    int itemsOnPage,
            @RequestParam(name = "currentPage", defaultValue = "1")
            @Parameter (name = "Текущая страница сообщений.",
                    description = "Необязательный параметр. Служит для корректного постраничного отображения сообщений и обращения к ним. По-умолчанию равен 1")
                    int currentPage) {
        PaginationData properties = new PaginationData(currentPage, itemsOnPage, MessagePageDtoByGroupChatId.class.getSimpleName());
        properties.getProps().put("groupChatId", groupChatId);
        if (chatDtoService.getGroupChatDtoById(groupChatId, properties).isPresent()) {
            return new ResponseEntity<>(chatDtoService.getGroupChatDtoById(groupChatId, properties).get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

    }

    @Operation (summary = "Получение сообщений single чата.", description = "Получение пагинированного списка сообщений single чата по его id.")
    @GetMapping("/{singleChatId}/single/message")
    public ResponseEntity<PageDTO<MessageDto>> getPagedMessagesOfSingleChat(
            @PathVariable("singleChatId")
            @Parameter(name = "Id single чата.", required = true, description = "Id single чата является обязательным параметром.")
                    long singleChatId,
            @RequestParam(name = "itemsOnPage", defaultValue = "10")
            @Parameter (name = "Количество сообщений на странице.",
                    description = "Необязательный параметр. Позволяет настроить количество сообщений на одной странице. По-умолчанию равен 10.")
                    int itemsOnPage,
            @RequestParam(name = "currentPage", defaultValue = "1")
            @Parameter (name = "Текущая страница сообщений.",
                    description = "Необязательный параметр. Служит для корректного постраничного отображения сообщений и обращения к ним. По-умолчанию равен 1")
                    int currentPage) {
        PaginationData properties = new PaginationData(currentPage, itemsOnPage, MessagePageDtoBySingleChatId.class.getSimpleName());
        properties.getProps().put("singleChatId", singleChatId);
        return new ResponseEntity<>(messagesPaginationService.getPageDto(properties), HttpStatus.OK);
    }
}
