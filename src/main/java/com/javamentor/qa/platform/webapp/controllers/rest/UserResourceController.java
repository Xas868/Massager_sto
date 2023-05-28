package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.dao.impl.pagination.user.UserPageDtoDaoAllUsersByRepImpl;
import com.javamentor.qa.platform.dao.impl.pagination.user.UserPageDtoDaoAllUsersImpl;
import com.javamentor.qa.platform.dao.impl.pagination.user.UserPageDtoDaoByVoteImpl;
import com.javamentor.qa.platform.models.dto.*;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.models.util.CalendarPeriod;
import com.javamentor.qa.platform.service.abstracts.dto.BookMarksDtoService;
import com.javamentor.qa.platform.service.abstracts.dto.UserDtoService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "User Resource Controller", description = "The User API")
public class UserResourceController {

    private final UserDtoService userDtoService;
    private final UserService userService;
    private final BookMarksDtoService bookMarksDtoService;

    public UserResourceController(UserDtoService userDtoService,
                                  UserService userService, BookMarksDtoService bookMarksDtoService) {
        this.userDtoService = userDtoService;
        this.userService = userService;
        this.bookMarksDtoService = bookMarksDtoService;
    }

    @GetMapping("/api/user/{userId}")
    @Operation(summary = "Получение dto пользователя по id",
            description = "Получение null, если пользователь не найден")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Возвращает список UserDto (id, email, fullName, imageLink, city, reputation, listTagDto)",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserDto.class))
                    }),
            @ApiResponse(responseCode = "403", description = "Доступ запрещён")
    })
    public ResponseEntity<UserDto> getUserDtoId(@PathVariable("userId") long id) {

        Optional<UserDto> userDto = userDtoService.findUserDtoById(id);

        if (userDto.isEmpty()) {
            return new ResponseEntity("Пользователь не найден!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userDto.get(), HttpStatus.OK);
    }

    @Operation(summary = "Получение пагинированного списка всех пользователей. " +
            "В запросе указываем page - номер страницы, items (по умолчанию 10) - количество результатов на странице",
            description = "Получение пагинированного списка всех пользователей отсортированных по дате создания без учета удаленных пользователей")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Возвращает пагинированный список PageDTO<UserDto> (id, email, fullName, imageLink, city, reputation, listTagDto)",
                    content = {
                            @Content(
                                    mediaType = "application/json")
                    }),
    })
    @GetMapping("/api/user/new")
    public ResponseEntity<PageDTO<UserDto>> paginationById(@RequestParam(defaultValue = "1") Integer page,
                                                           @RequestParam(defaultValue = "10") Integer items,
                                                           @RequestParam(required = false) String filter) {
        PaginationData data = new PaginationData(page, items,
                UserPageDtoDaoAllUsersImpl.class.getSimpleName(), filter);
        return new ResponseEntity<>(userDtoService.getPageDto(data), HttpStatus.OK);
    }

    @Operation(summary = "Постраничное получение списка пользователей",
            description = "Постраничное получение списка пользователей отсортированных по сумме голосов" +
                    "за ответы и вопросы, где DownVote = -1 и UpVote = 1")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Возвращает PageDto с вложенным массивом UserDto согласно текущей страницы" +
                            "и количеству запрашиваемых пользователей",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PageDTO.class))
                    }),
            @ApiResponse(responseCode = "403", description = "Доступ запрещён"),
            @ApiResponse(responseCode = "400", description = "Неверная нумерация страниц")
    })
    @GetMapping(path = "/api/user/vote")
    public ResponseEntity<PageDTO<UserDto>> getUsersByVoteAsc(@RequestParam(defaultValue = "1") Integer page,
                                                              @RequestParam(required = false, defaultValue = "10") Integer items,
                                                              @RequestParam(required = false) String filter) {
        PaginationData data = new PaginationData(page, items,
                UserPageDtoDaoByVoteImpl.class.getSimpleName(), filter);
        return new ResponseEntity<>(userDtoService.getPageDto(data), HttpStatus.OK);
    }

    @PatchMapping("/api/user/change/password")
    @Operation(summary = "Изменение пароля пользователя",
            description = "Пароль должен состоять из букв и цифр, " +
                    "должен быть длинее 6 символов и " +
                    "не должен совпадать с текущим паролем")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Изменеяет пароль ",
                    content = {
                            @Content(
                                    mediaType = "application/json"
                            )
                    }),
            @ApiResponse(responseCode = "403", description = "Доступ запрещён")
    })
    public ResponseEntity<?> changePassword(@RequestParam String password) {
        userService.changePassword(password,
                userService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Получение пагинированного списка всех пользователей. " +
            "В запросе указываем page - номер страницы, items (по умолчанию 10) - количество результатов на странице",
            description = "Получение пагинированного списка всех пользователей отсортированных по репутации без учета удаленных пользователей (аттрибут isDeleted=false)")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Возвращает пагинированный список PageDTO<UserDto> (id, email, fullName, imageLink, city, reputation, listTagDto)",
                    content = {
                            @Content(
                                    mediaType = "application/json")
                    }),
    })
    @GetMapping("/api/user/reputation")
    public ResponseEntity<PageDTO<UserDto>> getAllUserPaginationByReputation(@RequestParam(defaultValue = "1") Integer page,
                                                                             @RequestParam(required = false, defaultValue = "10") Integer items,
                                                                             @RequestParam(required = false) String filter) {
        PaginationData data = new PaginationData(page, items,
                UserPageDtoDaoAllUsersByRepImpl.class.getSimpleName(), filter);
        return new ResponseEntity<>(userDtoService.getPageDto(data), HttpStatus.OK);
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
    @GetMapping("/api/user/profile/questions")
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
    @GetMapping("/api/user/profile/delete/questions")
    public ResponseEntity<List<UserProfileQuestionDto>> getAllUserProfileQuestionDtoByUserIdIsDelete(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(userDtoService
                .getUserProfileQuestionDtoByUserIdIsDeleted(user.getId()),
                HttpStatus.OK);
    }
 /*
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
   @GetMapping("/api/user/profile/bookmarks")
    public ResponseEntity<List<BookMarksDto>> getAllBookMarksInUserProfile(@AuthenticationPrincipal User user) {
        return new ResponseEntity<T>(bookMarksDtoService
                .getAllBookMarksInUserProfile(user.getId()),
                HttpStatus.OK);
    }*/

    @Operation(summary = "Получение списка из топ 10 пользователей, оставивших наибольшее число ответов на вопросы за неделю",
            description = "Получение отсортированного списка из топ 10 пользователей (UserDto), оставивших наибольшее число ответов на вопросы за неделю. " +
                    "Если числа ответов у некоторых пользователей равны, то сортировка идёт по голосам, полученным за эти ответы по убыванию. Если голоса равны, то " +
                    "сортировка по id по возрастанию.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Возвращает список UserDto(id, email, fullName, imageLink, city, reputation, listTagDto)",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = UserDto.class)))
                    })
    })
    @GetMapping("/api/user/top")
    public ResponseEntity<List<UserDto>> getTopUsersForDaysRankedByNumberOfQuestions(
            @RequestParam(name = "usersCount", required = false, defaultValue = "10")
            @Parameter (name = "Количество юзеров, которых необходимо получить.",
                    description = "Необязательный параметр. Позволяет настроить количество сообщений на одной странице. По-умолчанию равен 10.")
            Integer usersCount,
            @RequestParam(name = "period", required = false, defaultValue = "week")
            @Parameter (name = "Количество дней, по которым будет идти поиск", description = "Необязательный параметр. Позволяет настроить пероид времени, " +
                    "по которому будет идти поиск. Значение по умолчанию \"week\" ")
            CalendarPeriod calendarPeriod) {
        return new ResponseEntity<>(userDtoService.getTopUsersForDaysRankedByNumberOfQuestions(calendarPeriod), HttpStatus.OK);
    }

    @Operation(summary = "Получение количества ответов авторизованного пользователя." ,
            description = "Контроллер возвращает целое число, которое отражает количество ответов авторизованного пользователя за неделю. В качестве параметра принимает авторизованного пользователя.")
    @Parameter (name = "user", description = "Авторизованный пользователь, количество ответов которого будет отображено.", required = true)
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Возвращает число ответов авторизованного пользователя.",
                    content = {
                            @Content(
                                    mediaType = "application/json")
                    })
    })
    @GetMapping ("api/user/profile/question/week")
    public ResponseEntity<Long> getAnswersPerWeekByUserId (@AuthenticationPrincipal User user) {
        return new ResponseEntity<Long>(userDtoService.getCountAnswersPerWeekByUserId(user.getId()), HttpStatus.OK);

    }
}
