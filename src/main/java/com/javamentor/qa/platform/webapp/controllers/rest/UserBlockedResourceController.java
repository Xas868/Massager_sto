package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.UserBlockDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.UserBlockDtoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user/")
@Tag(name = "User Blocked Resource Controller", description = "Управление заблокированными сущностями")
public class UserBlockedResourceController {

    private final UserBlockDtoService userBlockDtoService;

    public UserBlockedResourceController(UserBlockDtoService userBlockDtoService) {
        this.userBlockDtoService = userBlockDtoService;
    }

    @Operation(summary = "Получение списка заблокированных пользователей у авторизированного пользователя.",
            description = "Получение списка заблокированных пользователей у авторизированного пользователя.")
    @ApiResponse(responseCode = "200", description = "Список забокированных пользователей выведен", content = {
            @Content(mediaType = "application/json")
    })
    @ApiResponse(responseCode = "400", description = "Список забокированных пользователей не выведен", content = {
            @Content(mediaType = "application/json")
    })
    @GetMapping("/block")
    public ResponseEntity<List<UserBlockDto>> getAllBlockedUsers(@AuthenticationPrincipal User user){
        return new ResponseEntity<>(userBlockDtoService.getAllBlockedUsers(user.getId()), HttpStatus.OK);

    }

}
