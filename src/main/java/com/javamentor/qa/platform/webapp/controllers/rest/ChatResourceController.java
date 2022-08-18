package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.dao.abstracts.model.UserDao;
import com.javamentor.qa.platform.dao.impl.pagination.messagedto.MessagePageDtoByGroupChatId;
import com.javamentor.qa.platform.dao.impl.pagination.messagedto.MessagePageDtoBySingleChatId;
import com.javamentor.qa.platform.models.dto.ChatDto;
import com.javamentor.qa.platform.models.dto.CreateSingleChatDto;
import com.javamentor.qa.platform.models.dto.MessageDto;
import com.javamentor.qa.platform.models.dto.SingleChatDto;
import com.javamentor.qa.platform.models.dto.GroupChatDto;
import com.javamentor.qa.platform.models.dto.CreateGroupChatDto;
import com.javamentor.qa.platform.models.dto.PageDTO;
import com.javamentor.qa.platform.models.entity.chat.SingleChat;
import com.javamentor.qa.platform.models.entity.chat.GroupChat;
import com.javamentor.qa.platform.models.entity.chat.ChatType;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.ChatDtoService;
import com.javamentor.qa.platform.service.abstracts.model.SingleChatRoomService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import com.javamentor.qa.platform.service.abstracts.model.GroupChatRoomService;
import com.javamentor.qa.platform.service.abstracts.model.SingleChatService;
import com.javamentor.qa.platform.service.impl.dto.DtoServiceImpl;
import com.javamentor.qa.platform.service.impl.model.ChatRoomServiceImpl;
import com.javamentor.qa.platform.webapp.converters.GroupChatConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Tag(name = "ChatResourceController", description = "Позволяет работать с чатами")
@RestController
@RequestMapping("/api/user/chat")
public class ChatResourceController {
    private final DtoServiceImpl<MessageDto> messagesPaginationService;
    private final ChatDtoService chatDtoService;
    private final SingleChatRoomService singleChatRoomService;
    private final UserService userService;
    private final ChatRoomServiceImpl chatRoomServiceIml;
    private final GroupChatRoomService groupChatRoomService;
    private final GroupChatConverter groupChatConverter;
    private final UserDao userDao;
    private final SingleChatService singleChatService;

    @Autowired
    private ChatResourceController(DtoServiceImpl<MessageDto> messagesPaginationService, ChatDtoService chatDtoService, SingleChatRoomService singleChatRoomService, UserService userService, ChatRoomServiceImpl chatRoomServiceIml, GroupChatRoomService groupChatRoomService, GroupChatConverter groupChatConverter, UserDao userDao, SingleChatService singleChatService) {
        this.messagesPaginationService = messagesPaginationService;
        this.chatDtoService = chatDtoService;
        this.singleChatRoomService = singleChatRoomService;
        this.userService = userService;
        this.chatRoomServiceIml = chatRoomServiceIml;
        this.groupChatRoomService = groupChatRoomService;
        this.groupChatConverter = groupChatConverter;
        this.userDao = userDao;
        this.singleChatService = singleChatService;
    }

    @Operation(summary = "Поиск и сортировка чатов по указанному имени",
            description = "Получение листа чатов, (групповых и одиночных) содержащих заданное имя сортированных по убыванию даты последнего в них сообщения")
    @ApiResponse(responseCode = "200",
            description = "Чаты найдены",
            content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "400",
            description = "Чаты не найдены",
            content = @Content(mediaType = "application/json"))
    @GetMapping
    public ResponseEntity<List<ChatDto>> getChatsByName(
            @RequestParam(name = "name", defaultValue = "")
            @Parameter(name = "Строка по которой будет проходить поиск чатов",
                    description = "Необязательный параметр. Любое совпадение строки в названии чата, вернёт этот чат")
                    String chatName,
            Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        return new ResponseEntity<>(chatDtoService.getAllChatsByNameAndUserId(chatName, currentUser.getId()), HttpStatus.OK);
    }

    @GetMapping("/single")
    public ResponseEntity<List<SingleChatDto>> getAllSingleChatDtoByUserId(Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        return new ResponseEntity<>(chatDtoService.getAllSingleChatDtoByUserId(currentUser.getId()), HttpStatus.OK);
    }

    @Operation(summary = "Создание single чата с первым сообщением.", description = "Создание single чата.")
    @ApiResponse(responseCode = "200", description = "Single чат создан.", content = {
            @Content(mediaType = "application/json")
    })
    @ApiResponse(responseCode = "400", description = "Single чат не создан.", content = {
            @Content(mediaType = "application/json")
    })
    @PostMapping("/single")
    public ResponseEntity<SingleChatDto> createSingleChatDto(@RequestBody CreateSingleChatDto createSingleChatDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        Optional<User> destinationUser = userService.getById(createSingleChatDto.getUserId());
        if (destinationUser.isEmpty() || createSingleChatDto.getMessage() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        SingleChat singleChat = singleChatRoomService.createSingleChatAndFirstMessage(createSingleChatDto, currentUser, destinationUser);
        SingleChatDto singleChatDto = SingleChatDto.builder()
                .id(singleChat.getId())
                .name(singleChat.getUseTwo().getNickname())
                .lastMessage(createSingleChatDto.getMessage())
                .build();
        return new ResponseEntity<>(singleChatDto, HttpStatus.OK);
    }

    @Operation(summary = "Получение группового чата с сообщениями.", description = "Получение группового чата с пагинированным списком сообщений.")
    @ApiResponse(responseCode = "200", description = "Групповой чат найден", content = {
            @Content(mediaType = "application/json"),
    })
    @ApiResponse(responseCode = "400", description = "Групповой чат с указанными id не найден", content = {
            @Content(mediaType = "application/json"),
    })
    @GetMapping("/group/{groupChatId}")
    public ResponseEntity<GroupChatDto> getGroupChatDtoById(
            @PathVariable("groupChatId")
            @Parameter(name = "Id группового чата.", required = true, description = "Id группового чата является обязательным параметром.")
            long groupChatId,
            @RequestParam(name = "itemsOnPage", defaultValue = "10")
            @Parameter(name = "Количество сообщений на странице.",
                    description = "Необязательный параметр. Позволяет настроить количество сообщений на одной странице. По-умолчанию равен 10.")
            int itemsOnPage,
            @RequestParam(name = "currentPage", defaultValue = "1")
            @Parameter(name = "Текущая страница сообщений.",
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

    @Operation(summary = "Получение сообщений single чата.", description = "Получение пагинированного списка сообщений single чата по его id.")
    @GetMapping("/{singleChatId}/single/message")
    public ResponseEntity<PageDTO<MessageDto>> getPagedMessagesOfSingleChat(
            @PathVariable("singleChatId")
            @Parameter(name = "Id single чата.", required = true, description = "Id single чата является обязательным параметром.")
            long singleChatId,
            @RequestParam(name = "itemsOnPage", defaultValue = "10")
            @Parameter(name = "Количество сообщений на странице.",
                    description = "Необязательный параметр. Позволяет настроить количество сообщений на одной странице. По-умолчанию равен 10.")
            int itemsOnPage,
            @RequestParam(name = "currentPage", defaultValue = "1")
            @Parameter(name = "Текущая страница сообщений.",
                    description = "Необязательный параметр. Служит для корректного постраничного отображения сообщений и обращения к ним. По-умолчанию равен 1")
            int currentPage) {
        PaginationData properties = new PaginationData(currentPage, itemsOnPage, MessagePageDtoBySingleChatId.class.getSimpleName());
        properties.getProps().put("singleChatId", singleChatId);
        return new ResponseEntity<>(messagesPaginationService.getPageDto(properties), HttpStatus.OK);
    }

    @Operation(summary = "Удаление пользователя из чата.", description = "Удаление пользователя из чата по его id.")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserFromChatById(
            @PathVariable("id")
            @Parameter(name = "Id чата.", required = true, description = "Id чата является обязательным параметром.")
            Long chatId,
            Authentication authentication) {
        ChatType chatType = chatRoomServiceIml.getById(chatId).get().getChatType();
        User currentUser = (User) authentication.getPrincipal();

        if (chatType.equals(ChatType.GROUP)) {
            groupChatRoomService.deleteUserFromGroupChatById(chatId, currentUser.getId());
            return new ResponseEntity<>("GroupChat deleted", HttpStatus.OK);
        }

        singleChatService.deleteUserFromSingleChatById(chatId, currentUser.getId());
        return new ResponseEntity<>("SingleChat deleted", HttpStatus.OK);
    }

    @Operation(summary = "Создание group чата.", description = "Создание group чата")
    @ApiResponse(responseCode = "200",
            description = "Групповой чат создан",
            content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "400",
            description = "Групповой чат не создан",
            content = @Content(mediaType = "application/json"))
    @PostMapping("/group")
    public ResponseEntity<String> createGroupChatDto(@RequestBody CreateGroupChatDto createGroupChatDto) {
        List<Long> userIds = new ArrayList<>(createGroupChatDto.getUserIds());

        if (!userIds.isEmpty()) {
            List<Long> notExistUsers = userDao.getUnregisteredUserIds(userIds);
            if (!notExistUsers.isEmpty()) {
                return new ResponseEntity<>("Users: " + notExistUsers + "are not registered", HttpStatus.BAD_REQUEST);
            }
            groupChatRoomService.persist(groupChatConverter.createGroupChatDTOToGroupChat(createGroupChatDto));
            return new ResponseEntity<>("GroupChat created", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("List of user's ids is empty", HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "Добавление пользователя в group чат.", description = "Добавление пользователя в group чат по его id")
    @PostMapping("/group/{id}/join")
    public ResponseEntity<String> addUserInGroupChat(
            @PathVariable("id")
            @Parameter(name = "Id group чата.", required = true, description = "Id group чата является обязательным параметром.")
                    Long id,
            @RequestParam("userId")
            @Parameter(name = "id Пользователя", required = true, description = "Id пользователя является обязательным параметром.")
                    Long userId) {
        Optional<GroupChat> groupChat = groupChatRoomService.getGroupChatAndUsers(id);
        Optional<User> user = userService.getById(userId);

        if (user.isPresent() && groupChat.isPresent()) {
            Set<User> userSet = groupChat.get().getUsers();
            userSet.add(user.get());
            groupChatRoomService.update(groupChat.get());

            return new ResponseEntity<>("userAdded", HttpStatus.OK);
        }

        return new ResponseEntity<>("it's bad request", HttpStatus.BAD_REQUEST);
    }
}
