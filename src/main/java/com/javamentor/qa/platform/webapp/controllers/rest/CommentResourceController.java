package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.dao.impl.pagination.commentdto.AnswerCommentPageDtoDaoByIdImpl;
import com.javamentor.qa.platform.models.dto.AnswerCommentDto;
import com.javamentor.qa.platform.models.dto.PageDTO;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import com.javamentor.qa.platform.service.abstracts.dto.CommentDtoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "CommentResourceController", description = "Позволяет работать с комментариями")
@RestController
@RequestMapping("api/user/comment")
public class CommentResourceController {

    private final CommentDtoService commentDtoService;

    @Autowired
    public CommentResourceController(CommentDtoService commentDtoService) {
        this.commentDtoService = commentDtoService;
    }

    @GetMapping("/answer/{answerId}")
    @Operation(
            summary = "Получение списка комментариев по id ответа",
            description = "Получение пагинированного списка dto комментариев по id ответа"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Возвращает пагинированный список AnswerCommentDto " +
                    "(id, answerId, lastRedactionDate, persistDate, " +
                    "text, userId, imageLink, reputation)",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AnswerCommentDto.class)
                    )
            }
    )
    public ResponseEntity<PageDTO<AnswerCommentDto>> getAnswerCommentById(@PathVariable("answerId") Long answerId,
                                                                          @RequestParam(defaultValue = "1") Integer currentPage,
                                                                          @RequestParam(required = false, defaultValue = "10") Integer items) {
        PaginationData data = new PaginationData(currentPage, items, AnswerCommentPageDtoDaoByIdImpl.class.getSimpleName());
        data.getProps().put("answerId", answerId);
        return new ResponseEntity<>(commentDtoService.getPageDto(data), HttpStatus.OK);
    }
}
