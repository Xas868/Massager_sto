package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.entity.chat.Message;
import com.javamentor.qa.platform.models.entity.chat.MessageStar;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.MessageService;
import com.javamentor.qa.platform.service.abstracts.model.MessageStarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.callbacks.Callback;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
    @Schema
    public ResponseEntity<?> addMessageToStarMessages(@Parameter(schema = @Schema(example = "{" +
            "\"id\":1" +
            "}")) @RequestBody Message messageToStar) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        Message messageFromDB;
        try {
            messageFromDB = messageService.getById(messageToStar.getId()).get();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        MessageStar message = new MessageStar();
        message.setUser(user);
        message.setMessage(messageFromDB);
        messageStarService.persist(message);
        return new ResponseEntity<>("Message with id = " + messageToStar.getId() + " was successfully add to stars", HttpStatus.OK);
    }
}
