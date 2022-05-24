package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/")
public class AdminResourceController {

    private final UserService userService;
    private final AnswerService answerService;

    @Autowired
    public AdminResourceController(UserService userService, AnswerService answerService) {
        this.userService = userService;
        this.answerService = answerService;
    }

    @PutMapping("/disable-user/{id}")
    public ResponseEntity<Object> deleteUserWithId(@PathVariable("id") long id) {
        userService.deleteById(id);
        return new ResponseEntity<>("User with id = " + id + " was successfully disabled", HttpStatus.OK);
    }
    @Operation(
            summary = "Удаление ответа по id",
            description = "Удаление ответа id"
    )
    @ApiResponse(responseCode = "200", description = "  Ответ (answer) удален по id", content = {
            @Content(mediaType = "application/json")
    })
    @ApiResponse(responseCode = "400", description = "Ответ (answer) с таким id не существует",
            content = {
            @Content(mediaType = "application/json")
    })
    @DeleteMapping("/answer/{id}/delete")
    public ResponseEntity<?> deleteAnswerById(@PathVariable("id") Long id) {
        if (answerService.existsById(id)) {
            answerService.deleteById(id);
            return new ResponseEntity<>("Answer with id = " + id + " was successfully deleted", HttpStatus.OK);
        }
            return new ResponseEntity<>("Answer with id = " + id + " doesn't exist", HttpStatus.BAD_REQUEST);
    }
}
