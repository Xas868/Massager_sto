package com.javamentor.qa.platform.webapp.controllers.rest;


import com.javamentor.qa.platform.models.dto.BookMarksDto;
import com.javamentor.qa.platform.models.entity.GroupBookmark;
import com.javamentor.qa.platform.models.entity.bookmark.BookMarks;
import com.javamentor.qa.platform.models.entity.bookmark.SortBookmark;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.BookMarksDtoService;
import com.javamentor.qa.platform.service.abstracts.model.BookmarksService;
import com.javamentor.qa.platform.service.abstracts.model.GroupBookmarkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "ProfileBookmarkResourceController", description = "Позволяет работать с закладками пользователя")
@RequestMapping("/api/user/profile/bookmark")
public class ProfileBookmarkResourceController {

    private final BookMarksDtoService bookMarksDtoService;
    private final GroupBookmarkService groupBookmarkService;
    private final BookmarksService bookmarksService;


    public ProfileBookmarkResourceController(BookMarksDtoService bookMarksDtoService, GroupBookmarkService groupBookmarkService, BookmarksService bookmarksService) {
        this.bookMarksDtoService = bookMarksDtoService;
        this.groupBookmarkService = groupBookmarkService;
        this.bookmarksService = bookmarksService;

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
    public ResponseEntity<List<BookMarksDto>> getAllBookMarksInUserProfile(@AuthenticationPrincipal User user,
                                                                           @RequestParam(
                                                                                   required = false,
                                                                                   defaultValue = "NEW",
                                                                                   name = "sortBookmark"
                                                                           ) SortBookmark sortBookmark) {
        return new ResponseEntity<>(bookMarksDtoService
                .getAllBookMarksInUserProfile(user.getId(), sortBookmark), HttpStatus.OK);

    }

    @Operation(summary = "Получение списка названий групп пользователя",
            description = "Возвращает список имен(title) GroupBookMark")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Возвращает список имен(title) GroupBookMark",
                    content = {
                            @Content(
                                    mediaType = "application/json"
                            )
                    }
            )
    })
    @GetMapping("/bookmark/group")
    public ResponseEntity<List<String>> getAllUserBookMarkGroupNames(@AuthenticationPrincipal User user) {
        return new ResponseEntity<List<String>>(groupBookmarkService.getAllUserBookMarkGroupNamesByUserId(user.getId()), HttpStatus.OK);
    }

    @Operation(summary = "Создание новой группы закладок")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200",
                            description = "Создание новой группы закладок",
                            content = {
                                    @Content(
                                            mediaType = "application/json"
                                    )
                            })
            }
    )
    @PostMapping("/bookmark/group")
    public ResponseEntity addNewGroupBookMark(@AuthenticationPrincipal User user, @RequestBody(required = false) String title) {
        if (title == null || title.isEmpty()) {
            return new ResponseEntity<>("request body (title field) must not be empty", HttpStatus.BAD_REQUEST);
        }
        if (groupBookmarkService.isGroupBookMarkExistsByName(user.getId(), title)) {
            return new ResponseEntity<>("user already has group bookmark with title " + title, HttpStatus.BAD_REQUEST);
        }
        GroupBookmark groupBookmark = GroupBookmark.builder()
                .user(user)
                .title(title)
                .build();

        groupBookmarkService.persist(groupBookmark);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Удаление закладки авторизированного пользователя по questionId")
    @ApiResponse(responseCode = "200",
                            description = "Bookmark удален",
                            content = {
                                    @Content(
                                            mediaType = "application/json"
                                    )
                            })
    @ApiResponse(responseCode = "400",
                            description = "Bookmark с таким questionId не существует",
                            content = {
                                    @Content(
                                            mediaType = "application/json"
                                    )
                            })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBookmarkByQuestionId(@PathVariable("id") @RequestBody Long questionId){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<BookMarks> bookmarks = bookmarksService.getBookmarkByQuestionId(user.getId(), questionId);
        if (bookmarks.isPresent()) {
            bookmarksService.deleteById(questionId);
            return new ResponseEntity<>("Закладка с id = " + questionId + " была успешно удалена", HttpStatus.OK);
        }
        return new ResponseEntity<>("Закладка с id = " + questionId + " не существует", HttpStatus.BAD_REQUEST);
    }
}


