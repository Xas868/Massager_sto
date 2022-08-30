package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.entity.chat.Message;
import com.javamentor.qa.platform.models.entity.chat.MessageStar;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.MessageService;
import com.javamentor.qa.platform.service.abstracts.model.MessageStarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Tag(name = "MessageResourceController", description = "Позволяет работать с избранными сообщениями")
@RestController
@RequestMapping("/api/user/message")
public class MessageResourceController {
    private final MessageStarService messageStarService;
    private final MessageService messageService;

    @Autowired
    public MessageResourceController(MessageStarService messageStarService, MessageService messageService) {
        this.messageStarService = messageStarService;
        this.messageService = messageService;
    }

    @Operation(summary = "Сохранение сообщения в избранное аутентифицированного юзера",
            description = "Принимает ID сообщения, которое нужно добавить в избранное")
    @ApiResponse(responseCode = "200", description = "Удалось сохранить сообщение в избранное", content = {
            @Content(mediaType = "application/json")
    })
    @ApiResponse(responseCode = "400", description = "Не удалось сохранить, пришло некорректное ID", content = {
            @Content(mediaType = "application/json")
    })
    @PostMapping("/star")
    public ResponseEntity<?> addMessageToStarMessages(@RequestBody Long messageId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Message> messageToStar = messageService.getById(messageId);
        if (messageToStar.isPresent()) {
            MessageStar message = new MessageStar();
            message.setUser(user);
            message.setMessage(messageToStar.get());
            messageStarService.persist(message);
            return new ResponseEntity<>("Message with id = " + messageId + " was successfully add to stars", HttpStatus.OK);
        }
        return new ResponseEntity<>("Пришло некорректное ID(" + messageId + ") сообщения для сохранения", HttpStatus.BAD_REQUEST);
    }
}
