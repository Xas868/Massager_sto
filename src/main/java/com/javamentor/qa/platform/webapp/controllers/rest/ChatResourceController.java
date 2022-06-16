package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.ChatMessageDto;
import com.javamentor.qa.platform.models.entity.chat.Message;
import com.javamentor.qa.platform.models.entity.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@MessageMapping
@RequestMapping("/api/broadcast")
public class ChatResourceController {



     @Operation(
             summary = "Добавление сообщения в  групповой чат",
             description = "Добавление сообщения в чат от юзера в чате"
     )
     @ApiResponse(responseCode = "200", description = "Сообщение добавлено", content = {
             @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
     })
     @ApiResponse(responseCode = "400", description = "Сообщение не добавлено не добавлен", content = {
             @Content(mediaType = "application/json")
     })
     @PostMapping("api/user/addingroupchat")
     public ResponseEntity<?> createMessagesForGroupChat(@RequestBody ChatMessageDto chatMessageDto,
                                                         Authentication auth){
          User user = (User) auth.getPrincipal();
          Long user_id = user.getId();
          chatMessageDto.setUserSenderId(user_id);
     return new ResponseEntity<>(chatMessageDto,HttpStatus.OK);






     }

}
