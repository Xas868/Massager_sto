package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.dao.abstracts.model.ReadOnlyDao;
import com.javamentor.qa.platform.dao.abstracts.model.ReadWriteDao;
import com.javamentor.qa.platform.dao.impl.pagination.commentdto.AnswerCommentPageDtoDaoByIdImpl;
import com.javamentor.qa.platform.dao.impl.pagination.commentdto.CommentPageDtoDaoCommentsOfQuestion;
import com.javamentor.qa.platform.models.dto.AnswerCommentDto;
import com.javamentor.qa.platform.models.dto.CommentDto;
import com.javamentor.qa.platform.models.dto.PageDTO;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import com.javamentor.qa.platform.models.entity.question.CommentQuestion;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.question.answer.CommentAnswer;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.CommentDtoService;
import com.javamentor.qa.platform.service.abstracts.model.CommentQuestionService;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Tag(name = "CommentResourceController", description = "Позволяет работать с комментариями")
@RestController
@RequestMapping("api/user/comment")
public class CommentResourceController {

    private final QuestionService questionService;
    private final CommentQuestionService commentQuestionService;
    private final CommentDtoService commentDtoService;
    private ReadWriteDao commentAnswerService;

    @Autowired
    public CommentResourceController(QuestionService questionService, CommentQuestionService commentQuestionService, CommentDtoService commentDtoService) {
        this.questionService = questionService;
        this.commentQuestionService = commentQuestionService;
        this.commentDtoService = commentDtoService;
    }

    @Operation(
            summary = "Добавление комментария в вопрос",
            description = "Добавление комментария в вопрос"
    )
    @ApiResponse(responseCode = "200", description = "Комментарий добавлен", content = {
            @Content(mediaType = "application/json")
    })
    @ApiResponse(responseCode = "400", description = "Комментарий не добавлен", content = {
            @Content(mediaType = "application/json")
    })
    @PostMapping("/question/{questionId}")
    public ResponseEntity<?> addCommentQuestion(@PathVariable Long questionId, @RequestBody String textComment,
                                                Authentication auth){
        User user = (User) auth.getPrincipal();
        Optional<Question> question = questionService.getById(questionId);
        if (question.isEmpty()) {
            return new ResponseEntity<>("There is not question " + questionId.toString(), HttpStatus.BAD_REQUEST);
        }
        CommentQuestion commentQuestion = new CommentQuestion(textComment, user);
        commentQuestion.setQuestion(question.get());
        commentQuestionService.persist(commentQuestion);
        return new ResponseEntity<>("Comment successfully added", HttpStatus.OK);
    }

    @Operation(
            summary = "Получение пагинированного списка комментариев к вопросу по id",
            description = "Получение пагинированного списка комментариев к вопросу по id"
    )
    @ApiResponse(responseCode = "200", description = "Комментарии получены", content = {
            @Content(mediaType = "application/json")
    })
    @ApiResponse(responseCode = "400", description = "Комментарии не получены", content = {
            @Content(mediaType = "application/json")
    })
    @GetMapping("/question/{questionId}")
    public ResponseEntity<?> getCommentsOfQuestion(@PathVariable Long questionId, @RequestParam(defaultValue = "1") int currentPage,
                                                   @RequestParam(defaultValue = "10") int items) {
        PaginationData data = new PaginationData(
                currentPage,
                items,
                CommentPageDtoDaoCommentsOfQuestion.class.getSimpleName());
        data.getProps().put("questionId", questionId);
        return new ResponseEntity<>(commentDtoService.getPageDto(data), HttpStatus.OK);
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
    public ResponseEntity<PageDTO<CommentDto>> getAnswerCommentById(@PathVariable("answerId") Long answerId,
                                                                    @RequestParam(defaultValue = "1") Integer currentPage,
                                                                    @RequestParam(required = false, defaultValue = "10") Integer items) {
        PaginationData data = new PaginationData(currentPage, items, AnswerCommentPageDtoDaoByIdImpl.class.getSimpleName());
        data.getProps().put("answerId", answerId);
        return new ResponseEntity<>(commentDtoService.getPageDto(data), HttpStatus.OK);
    }
    @Operation(
            summary = "Добавление комментария к ответу",
            description = "Добавление комментария к ответу"
    )
    @ApiResponse(responseCode = "200", description = "Комментарий добавлен", content = {
            @Content(mediaType = "application/json")
    })
    @ApiResponse(responseCode = "400", description = "Комментарий не добавлен", content = {
            @Content(mediaType = "application/json")
    })
    @PostMapping("/{id}")
    public ResponseEntity<?> addCommentAnswer(@PathVariable Long id, @RequestBody String bodyComment,
                                              Authentication auth) {
        User user = (User) auth.getPrincipal();
        ReadOnlyDao answerService = null;
        Optional<Answer> answer = answerService.getById(id);
        if (answer.isEmpty()) {
            return new ResponseEntity<>("There is no answer " + id.toString(), HttpStatus.BAD_REQUEST);
        }
        CommentAnswer commentAnswer = new CommentAnswer(bodyComment, user);
        commentAnswer.setAnswer(answer.get());
        commentAnswerService.persist(commentAnswer);
        return new ResponseEntity<>("Comment successfully added", HttpStatus.OK);
    }






}

