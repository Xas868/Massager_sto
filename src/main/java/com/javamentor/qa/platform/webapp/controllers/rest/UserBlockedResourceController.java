package com.javamentor.qa.platform.webapp.controllers.rest;


import com.javamentor.qa.platform.models.entity.bookmark.BookMarks;
import com.javamentor.qa.platform.models.entity.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user/")
public class UserBlockedResourceController {
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
    @DeleteMapping("/bookmark/{id}")
    public ResponseEntity<?> deleteUserByUserId(@PathVariable("id") @RequestBody Long questionId){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<BookMarks> bookmarks = bookmarksService.getBookmarkByQuestionIdAndUserId(user.getId(), questionId);
        if (bookmarks.isPresent()) {
            bookmarksService.deleteById(questionId);
            return new ResponseEntity<>("Закладка с id = " + questionId + " была успешно удалена", HttpStatus.OK);
        }
        return new ResponseEntity<>("Закладка с id = " + questionId + " не существует", HttpStatus.BAD_REQUEST);
    }



}
