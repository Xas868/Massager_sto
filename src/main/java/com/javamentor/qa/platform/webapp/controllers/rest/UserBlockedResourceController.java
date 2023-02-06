package com.javamentor.qa.platform.webapp.controllers.rest;


import com.javamentor.qa.platform.models.entity.chat.BlockChatUserList;
import com.javamentor.qa.platform.models.entity.user.User;

import com.javamentor.qa.platform.service.abstracts.model.BlockChatUserListService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/user")
public class UserBlockedResourceController {

    private final BlockChatUserListService userBlockedService;
    private final UserService userService;

    public UserBlockedResourceController(BlockChatUserListService userBlockedService, UserService userService) {
        this.userBlockedService = userBlockedService;
        this.userService = userService;
    }


    @Operation(summary = "Удаление данного пользователя из блока у авторизированного пользователя")
    @ApiResponse(responseCode = "200",
            description = "Пользователь удален",
            content = {
                    @Content(
                            mediaType = "application/json"
                    )
            })
    @ApiResponse(responseCode = "400",
            description = "Пользователь с таким userId не существует",
            content = {
                    @Content(
                            mediaType = "application/json"
                    )
            })
    @DeleteMapping("/{userId}/block")
    public ResponseEntity<?> deleteBlockedUserByUserId(@PathVariable("userId") Long userId){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            userBlockedService.deleteUserFromBlockById(user.getId(),userId);
            return new ResponseEntity<>("Пользователь с id = " + userId + " был успешно удален", HttpStatus.OK);
        }

    @Operation(summary = "Добавление пользователя в блок лист у авторизированного пользователя")
    @ApiResponse(responseCode = "200",
            description = "Пользователь добавлен в блок лист",
            content = {
                    @Content(
                            mediaType = "application/json"
                    )
            })
    @PostMapping("/{userId}/block")
    public ResponseEntity<?> addBlockedUserByUserId(@PathVariable("userId") Long userId){
        User profile = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User blocked = userService.getById( userId).get();
        BlockChatUserList blockChatUserList = new BlockChatUserList(profile,blocked);
        userBlockedService.persist(blockChatUserList);
        return new ResponseEntity<>("Пользователь с id = " + userId + " был успешно добавлен в ваш блок лист", HttpStatus.OK);
    }

    }




