package com.javamentor.qa.platform.webapp.controllers.rest;


import com.javamentor.qa.platform.models.entity.user.User;

import com.javamentor.qa.platform.service.abstracts.model.UserBlockedService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/")
public class UserBlockedResourceController {




    private final UserBlockedService userBlockedService;

    public UserBlockedResourceController(UserBlockedService userBlockedService) {
        this.userBlockedService = userBlockedService;
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
    @DeleteMapping(" {userId}/delete ")
    public ResponseEntity<?> deleteBlockedUserByUserId(@PathVariable("userId") long userId){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


            userBlockedService.deleteUserFromBlockById(user.getId(),userId);
            return new ResponseEntity<>("Пользователь с id = " + userId + " был успешно удален", HttpStatus.OK);
        }


//        return new ResponseEntity<>("Закладка с id = " + questionId + " не существует", HttpStatus.BAD_REQUEST);
    }




