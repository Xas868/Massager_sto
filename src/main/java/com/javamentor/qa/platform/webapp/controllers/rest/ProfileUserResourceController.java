package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.dao.impl.pagination.user.profile.UserProfileAnswerPageDtoDaoImpl;
import com.javamentor.qa.platform.dao.impl.pagination.user.profile.UserProfileQuestionsPageDtoDaoImpl;
import com.javamentor.qa.platform.dao.impl.pagination.user.profile.UserProfileReputationPageDtoDaoImpl;
import com.javamentor.qa.platform.models.dto.*;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import com.javamentor.qa.platform.models.entity.question.ProfileQuestionSort;
import com.javamentor.qa.platform.models.entity.question.ProfileReputationSort;
import com.javamentor.qa.platform.models.entity.question.answer.ProfileAnswerSort;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.*;
import com.javamentor.qa.platform.service.abstracts.model.GroupBookmarkService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import com.javamentor.qa.platform.service.impl.dto.UserProfileAnswerPageDtoDaoServiceImpl;
import com.javamentor.qa.platform.service.impl.dto.UserProfileReputationPageDtoDaoServiceImpl;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "ProfileUserResourceController", description = "Позволяет работать с профилем пользователя")
@RestController
@RequestMapping("/api/user/profile")

public class ProfileUserResourceController {

    private final UserService userService;
    private final UserDtoService userDtoService;
    private final BookMarksDtoService bookMarksDtoService;
    private final UserProfileAnswerPageDtoDaoServiceImpl userProfileAnswerPageDtoDaoService;
    private final GroupBookmarkService groupBookmarkService;
    private final ProfileUserDtoService profileUserDtoService;
    private final UserProfileTagDtoService userProfileTagDtoService;
    private final UserProfileReputationPageDtoDaoService userProfileReputationPageDtoDaoService;

    public ProfileUserResourceController(
            UserService userService, UserDtoService userDtoService, BookMarksDtoService bookMarksDtoService, UserProfileAnswerPageDtoDaoServiceImpl userProfileAnswerPageDtoDaoService, ProfileUserDtoService profileUserDtoService, UserProfileTagDtoService userProfileTagDtoService, GroupBookmarkService groupBookmarkService, UserProfileReputationPageDtoDaoServiceImpl userProfileReputationPageDtoDaoService) {

        this.userService = userService;
        this.userDtoService = userDtoService;
        this.bookMarksDtoService = bookMarksDtoService;
        this.userProfileAnswerPageDtoDaoService = userProfileAnswerPageDtoDaoService;
        this.profileUserDtoService = profileUserDtoService;
        this.userProfileTagDtoService = userProfileTagDtoService;
        this.groupBookmarkService = groupBookmarkService;
        this.userProfileReputationPageDtoDaoService = userProfileReputationPageDtoDaoService;
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
    public ResponseEntity<PageDTO<UserProfileQuestionDto>> getAllUserProfileQuestionDtoById(@AuthenticationPrincipal User user,
                                                                                            @RequestParam(required = false, defaultValue = "VOTE", name = "sort") ProfileQuestionSort profileQuestionSort,
                                                                                            @RequestParam(required = false, defaultValue = "1") int currentPage,
                                                                                            @RequestParam(required = false, defaultValue = "10") int items) {

        PaginationData data = new PaginationData(currentPage, items, UserProfileQuestionsPageDtoDaoImpl.class.getSimpleName());
        data.getProps().put("userId", user.getId());
        data.getProps().put("sorted", profileQuestionSort);


        return new ResponseEntity<>(profileUserDtoService.getPageDto(data), HttpStatus.OK);
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


    @Operation(summary = "Получение количества ответов авторизованного пользователя.",
            description = "Контроллер возвращает целое число, которое отражает количество ответов авторизованного пользователя за неделю. " +
                          "В качестве параметра принимает авторизованного пользователя.")
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

    @Operation(summary = "Получение всех тегов авторизованного пользователя " +
            "Параметры запроса не требуются",
            description = "Получение списка UserProfileTagDto авторизованного пользователя ")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Возвращает список List<UserProfileTagDto> (id, tagName, countVoteTag, countAnswerQuestion)",
                    content = {
                            @Content(
                                    mediaType = "application/json")
                    }),
    })

    @GetMapping("/tags")
    public ResponseEntity<List<UserProfileTagDto>> getUserProfileTagDto(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(userProfileTagDtoService.getAllUserProfileTagDtoByUserId(user.getId()), HttpStatus.OK);
    }

    @Operation(summary = "Получение пагинированного списка ответов авторизированного пользователя " +
            "возвращается пагинированный список объектов UserProfileAnswerDto ",
            description = "Получение пагинированного списка ответов авторизированного пользователя ")
    @Parameter(name = "sort", description = "есть не обязательный параметр sort VOTE - по голосам, NEW - по дате. По умолчанию сортируется по голосам")
    @Parameter(name = "page", description = "есть не обязательный параметр page - указывает на страницу")
    @Parameter(name = "items", description = "есть не обязательный параметр items - количество ответов в одной странице")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Возвращает список UserProfileAnswerDto(long answerId,String title, Long vote, Long view, Long questionId, LocalDateTime persistDateTime)",
                    content = {
                            @Content(mediaType = "application/json")
                    }),
    })
    @GetMapping("/answers")
    public ResponseEntity<PageDTO<UserProfileAnswerDto>> getAllUserProfileQuestionDtoById(@AuthenticationPrincipal User user,
                                                                                          @RequestParam(required = false, defaultValue = "VOTE", name = "sort") ProfileAnswerSort profileAnswerSort,
                                                                                          @RequestParam(required = false, defaultValue = "1") int page,
                                                                                          @RequestParam(defaultValue = "10") int items) {
        PaginationData data = new PaginationData(page, items, UserProfileAnswerPageDtoDaoImpl.class.getSimpleName());
        data.getProps().put("user", user);
        data.getProps().put("userId", user.getId());
        data.getProps().put("profileAnswerSort", profileAnswerSort);

        return new ResponseEntity<>(userProfileAnswerPageDtoDaoService.getPageDto(data), HttpStatus.OK);
    }




    @Operation (summary = "Возвращает пангинированный список истории получения репутации в профиле пользователя")
    @Parameter(name = "items", description = "количество ответов в одной странице")
    @Parameter(name = "currentPage", description = "указывает на страницу")
    @ApiResponses (value =  {
            @ApiResponse(responseCode = "200",
                    description = "Возвращает пангинированный список истории получения репутации в профиле пользователя",
                    content = {
                            @Content(
                                    mediaType = "application/json"
                            )
                    })
    })
    @GetMapping ("/reputation")
    public ResponseEntity <PageDTO<UserProfileReputationDto>> getUserProfileReputation (@AuthenticationPrincipal User user,
                                                                                        @RequestParam(required = false, defaultValue = "NEW", name = "sort") ProfileReputationSort profileReputationSort,
                                                                                        @RequestParam (required = false,defaultValue = "1") int currenPage,
                                                                                        @RequestParam (defaultValue = "10") int items){
        PaginationData data = new PaginationData(currenPage, items, UserProfileReputationPageDtoDaoImpl.class.getSimpleName());
        data.getProps().put("userId", user.getId());
        data.getProps().put("profileReputationSort",profileReputationSort);

        return new ResponseEntity<>(userProfileReputationPageDtoDaoService.getPageDto(data),HttpStatus.OK);
    }
}