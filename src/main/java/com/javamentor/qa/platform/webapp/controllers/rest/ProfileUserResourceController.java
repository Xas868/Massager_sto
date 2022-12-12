package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.BookMarksDto;
import com.javamentor.qa.platform.models.dto.UserProfileQuestionDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.BookMarksDtoService;
import com.javamentor.qa.platform.service.abstracts.dto.UserDtoService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "ProfileUserResourceController", description = "Позволяет работать с профилем пользователя")
@RestController
@RequestMapping("/api/user/profile")

public class ProfileUserResourceController {

    private final UserService userService;
    private final UserDtoService userDtoService;
    private final BookMarksDtoService bookMarksDtoService;

    public ProfileUserResourceController(
                                  UserService userService,UserDtoService userDtoService,BookMarksDtoService bookMarksDtoService) {

        this.userService = userService;
        this.userDtoService = userDtoService;
        this.bookMarksDtoService = bookMarksDtoService;

    }


    @Operation(summary = "Получение всех вопросов авторизированного пользователя неотсортированных" +
            "В запросе нет параметров,возвращается список объектов UserProfileQuestionDto ",
            description = "Получение всех вопросов авторизированного пользователя")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Возвращает список UserProfileQuestionDto(long questionId,String title, List<TagDto>, Long answerCount, LocalDateTime persistDateTime)",
                    content = {
                            @Content(
                                    mediaType = "application/json")
                    }),
    })
    @GetMapping("/questions")
    public ResponseEntity<List<UserProfileQuestionDto>> getAllUserProfileQuestionDtoById(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(userDtoService.getAllUserProfileQuestionDtoById(user.getId()), HttpStatus.OK);
    }


    @Operation(summary = "Получение всех удаленных вопросов в виде UserProfileQuestionDto по email авторизованного пользователя " +
            "Параметры запроса не требуются",
            description = "Получение списка UserProfileQuestionDto на основе вопросов авторизованного пользователя,которые имеют статус isDeleted ")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Возвращает список List<UserProfileQuestionDto> (questionId, title, listTagDto, countAnswer, persistDateTime)",
                    content = {
                            @Content(
                                    mediaType = "application/json")
                    }),
    })
    @GetMapping("/delete/questions")
    public ResponseEntity<List<UserProfileQuestionDto>> getAllUserProfileQuestionDtoByUserIdIsDelete(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(userDtoService
                .getUserProfileQuestionDtoByUserIdIsDeleted(user.getId()),
                HttpStatus.OK);
    }

    @Operation(summary = "Получение всех закладок в профиле пользователя в виде BookMarksDto" +
            "Параметры запроса не требуются",
            description = "Получение всех закладок в профиле пользователя в виде BookMarksDto")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Возвращает список List<BookMarksDto> (questionId, title, listTagDto, countAnswer, countVote, countView, persistDateTime)",
                    content = {
                            @Content(
                                    mediaType = "application/json")
                    }),
    })
    @GetMapping("/bookmarks")
    public ResponseEntity<List<BookMarksDto>> getAllBookMarksInUserProfile(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(bookMarksDtoService
                .getAllBookMarksInUserProfile(user.getId()),
                HttpStatus.OK);
    }
    @Operation(summary = "Получение количества ответов авторизованного пользователя.",
            description = "Контроллер возвращает целое число, которое отражает количество ответов авторизованного пользователя за неделю. В качестве параметра принимает авторизованного пользователя.")
    @Parameter(name = "user", description = "Авторизованный пользователь, количество ответов которого будет отображено.", required = true)
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Возвращает число ответов авторизованного пользователя.",
                    content = {
                            @Content(
                                    mediaType = "application/json")
                    })
    })
    @GetMapping("/question/week")
    public ResponseEntity<Long> getAnswersPerWeekByUserId(@AuthenticationPrincipal User user) {
        return new ResponseEntity<Long>(userDtoService.getCountAnswersPerWeekByUserId(user.getId()), HttpStatus.OK);

    }
}
