package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.dao.impl.pagination.questiondto.QuestionPageDtoDaoAllQuestionsImpl;
import com.javamentor.qa.platform.dao.impl.pagination.questiondto.QuestionPageDtoDaoByTagId;
import com.javamentor.qa.platform.dao.impl.pagination.questiondto.QuestionPageDtoDaoSortedByImpl;
import com.javamentor.qa.platform.exception.ConstrainException;
import com.javamentor.qa.platform.models.dto.PageDTO;
import com.javamentor.qa.platform.models.dto.QuestionCreateDto;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.dto.QuestionViewDto;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import com.javamentor.qa.platform.models.entity.question.DateFilter;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.QuestionViewSort;
import com.javamentor.qa.platform.models.entity.question.TrackedTag;
import com.javamentor.qa.platform.models.entity.question.VoteQuestion;
import com.javamentor.qa.platform.models.entity.question.answer.VoteType;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.QuestionDtoService;
import com.javamentor.qa.platform.service.abstracts.dto.TagDtoService;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import com.javamentor.qa.platform.service.abstracts.model.QuestionViewedService;
import com.javamentor.qa.platform.service.abstracts.model.TrackedTagService;
import com.javamentor.qa.platform.service.abstracts.model.VoteQuestionService;
import com.javamentor.qa.platform.webapp.converters.QuestionConverter;
import com.javamentor.qa.platform.webapp.converters.TagConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.yaml.snakeyaml.events.Event;

import javax.persistence.Id;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("api/user/question")
@RestController
@Tag(name = "Question Resource Controller", description = "Управление сущностями, которые связаны с вопросами")
@AllArgsConstructor

public class QuestionResourceController {

    private final QuestionService questionService;
    private final VoteQuestionService voteQuestionService;
    private final QuestionDtoService questionDtoService;
    private final QuestionConverter questionConverter;
    private final TagConverter tagConverter;
    private final QuestionViewedService questionViewedService;
    private final TagDtoService tagDtoService;
    private final TrackedTagService trackedTagService;
    private TagDtoService tagDtoService1;

    @GetMapping("/count")
    @Operation(summary = "Количество всего вопросов в бд")
    @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Question.class))
    })
    @ApiResponse(responseCode = "400", description = "Неверные учетные данные", content = {
            @Content(mediaType = "application/json")
    })
    public ResponseEntity<Optional<Long>> getCountQuestion() {
        Optional<Long> countQuestion = questionService.getCountByQuestion();
        return new ResponseEntity<>(countQuestion, HttpStatus.OK);
    }

    @PostMapping("/{questionId}/upVote")
    @Operation(
            summary = "Голосование ЗА вопрос",
            description = "Устанавливает голос +1 за вопрос и +10 к репутации автора вопроса"
    )
    public ResponseEntity<?> upVote(@PathVariable("questionId") Long questionId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        Long userId = user.getId();
        Question question = questionService
                .getQuestionByIdWithAuthor(questionId)
                .orElseThrow(() -> new ConstrainException("Can't find question with id:" + questionId));
        int countUpVote = 10;
        if (voteQuestionService.validateUserVoteByQuestionIdAndUserId(questionId, userId)) {
            VoteQuestion voteQuestion = new VoteQuestion(user, question, VoteType.UP_VOTE, countUpVote);
            voteQuestionService.persist(voteQuestion);
            return new ResponseEntity<>(voteQuestionService.getVoteByQuestionId(questionId), HttpStatus.OK);
        }
        return new ResponseEntity<>("User was voting", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/{questionId}/downVote")
    @Operation(
            summary = "Голосование ПРОТИВ вопроса",
            description = "Устанавливает голос -1 за вопрос и -5 к репутации автора вопроса"
    )
    public ResponseEntity<?> downVote(@PathVariable("questionId") Long questionId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        Long userId = user.getId();
        Question question = questionService
                .getQuestionByIdWithAuthor(questionId)
                .orElseThrow(() -> new ConstrainException("Can't find question with id:" + questionId));
        int countDownVote = -5;
        if (voteQuestionService.validateUserVoteByQuestionIdAndUserId(questionId, userId)) {
            VoteQuestion voteQuestion = new VoteQuestion(user, question, VoteType.DOWN_VOTE, countDownVote);
            voteQuestionService.persist(voteQuestion);
            return new ResponseEntity<>(voteQuestionService.getVoteByQuestionId(questionId), HttpStatus.OK);
        }
        return new ResponseEntity<>("User was voting", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение информации по вопросу пользователя")
    @ApiResponse(responseCode = "200", description = "Информация по вопросу", content = {
            @Content(mediaType = "application/json")
    })

    public ResponseEntity<?> getQuestion(@PathVariable Long id) {
        Optional<QuestionDto> q = questionDtoService.getQuestionDtoServiceById(id);
        if (q.isPresent()) {
            return new ResponseEntity<>(q.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Question number not exist!", HttpStatus.BAD_REQUEST);
    }

    @Operation(
            summary = "Добавление вопроса",
            description = "Добавление вопроса"
    )
    @ApiResponse(responseCode = "200", description = "Вопрос добавлен", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = QuestionCreateDto.class))
    })
    @ApiResponse(responseCode = "400", description = "Вопрос не добавлен", content = {
            @Content(mediaType = "application/json")
    })
    @PostMapping("")
    public ResponseEntity<?> createNewQuestion(@Valid @RequestBody QuestionCreateDto questionCreateDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Question question = questionConverter.questionDtoToQuestion(questionCreateDto);
        User user = (User) authentication.getPrincipal();
        question.setUser(user);
        question.setTags(tagConverter.listTagDtoToListTag(questionCreateDto.getTags()));
        questionService.persist(question);
        return new ResponseEntity<>(questionConverter.questionToQuestionDto(question), HttpStatus.OK);
    }



    @GetMapping("/tag/{id}")
    @Operation(
            summary = "Получение списка вопросов по tag id",
            description = "Получение пагинированного списка dto вопросов по id тэга"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Возвращает пагинированный список QuestionDto " +
                    "(id, title, authorId, authorReputation, authorName, authorImage, description, viewCount," +
                    "countAnswer, countValuable, persistDateTime, lastUpdateDateTime, listTagDto)",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = QuestionViewDto.class)
                    )
            }
    )
    public ResponseEntity<PageDTO<QuestionViewDto>> getPageQuestionsByTagId(@PathVariable Long id,
                                                                            @RequestParam int page,
                                                                            @RequestParam(defaultValue = "10") int items,
                                                                            @RequestParam(required = false, defaultValue = "ALL") DateFilter dateFilter) {
        PaginationData data = new PaginationData(
                page, items, QuestionPageDtoDaoByTagId.class.getSimpleName()
        );
        data.getProps().put("id", id);
        data.getProps().put("dateFilter", dateFilter.getDay());

        return new ResponseEntity<>(questionDtoService.getPageDto(data), HttpStatus.OK);
    }


    @GetMapping("")
    @Operation(summary = "Получение пагинированного списка вопросов с возможностью учета trackedTag и ignoredTag",
            description = "Получение пагинированного списка вопросов пользователя, " +
                    "в запросе указываем page - номер страницы, обязательный параметр, items (по умолчанию 10) - количество результатов на странице," +
                    "не обязательный на фронте, trackedTag - не обязательный параметр, ignoredTag - не обязательный параметр")
    @ApiResponse(responseCode = "200", description = "Возвращает пагинированный список PageDTO<QuestionDTO> (id, title, authorId," +
            " authorReputation, authorName, authorImage, description, viewCount, countAnswer, countValuable," +
            " LocalDateTime, LocalDateTime, listTagDto", content = {
            @Content(mediaType = "application/json")
    })
    public ResponseEntity<PageDTO<QuestionViewDto>> allQuestionsWithTrackedTagsAndIgnoredTags(@RequestParam int page,
                                                                                              @RequestParam(required = false, defaultValue = "10") int items,
                                                                                              @RequestParam(required = false, defaultValue = "ALL") DateFilter dateFilter,
                                                                                              Authentication auth) throws NoSuchFieldException {

        PaginationData data = new PaginationData(page, items, QuestionPageDtoDaoAllQuestionsImpl.class.getSimpleName());
        User user = (User) auth.getPrincipal();


        data.getProps().put("trackedTags", tagDtoService.getTrackedTagsIdByUserId(user.getId()));
        data.getProps().put("ignoredTags", tagDtoService.getIgnoredTagsIdByUserId(user.getId()));
        data.getProps().put("userId", user.getId());
        data.getProps().put("dateFilter", dateFilter.getDay());


        return new ResponseEntity<>(questionDtoService.getPageDto(data), HttpStatus.OK);
    }

    @Operation(
            summary = "Помечает вопрос как прочитанный",
            description = "Помечает вопрос как прочитанный"
    )
    @ApiResponse(responseCode = "200", description = "Метод выполнен без ошибок", content = {
            @Content(mediaType = "application/json")
    })
    @ApiResponse(responseCode = "403", description = "Пользователь не аутентифицирован", content = {
            @Content(mediaType = "application/json")
    })
    @PostMapping("/{id}/view")
    public ResponseEntity<String> markQuestionLikeViewed(@PathVariable Long id, Authentication auth) {

        User user = (User) auth.getPrincipal();
        Optional<Question> question = questionService.getById(id);

        if (question.isPresent()) {
            questionViewedService.markQuestionLikeViewed(user, question.get());
            return new ResponseEntity<>("OK", HttpStatus.OK);
        }

        return new ResponseEntity<>("There is no question " + id.toString(), HttpStatus.BAD_REQUEST);
    }


    @GetMapping("/sorted")
    @Operation(
            summary = "Получение пагинированного списка всех вопросов с фильтрацией по тегам авторизированного пользователя.",

            description = "Получение пагинированного списка вопросов за весь период с сортировкой по списка по параметрам:" +
                          "NEW - сортировка от новых к старым," +
                          "NoAnswer - сортировка от вопросов, в которых нет ответов," +
                          "VIEW - сортировка по просмотрам (от большего к меньшему)" +

                          "В запросе указываем page - номер страницы, обязательный параметр, items (по умолчанию 10) - " +
                          "количество результатов на странице."
    )

    @ApiResponse(
            responseCode = "200",

            description = "Возвращает пагинированный список PageDTO<QuestionViewDTO> " +
                                                "(id, title, authorId, " +
                                                "authorReputation, authorName, authorImage, description, " +
                                                "viewCount, countAnswer, countValuable," +
                                                " LocalDateTime, LocalDateTime, listTagDto)",

            content = {@Content(mediaType = "application/json")}
    )

    public ResponseEntity<PageDTO<QuestionViewDto>> getAllQuestionsSortedBy(
            @RequestParam int page,
            @RequestParam(defaultValue = "10") int items,
            @RequestParam(required = false, defaultValue = "ALL") DateFilter dateFilter,
            @RequestParam(required = false, defaultValue = "NEW", name = "sortedBy") QuestionViewSort questionViewSort,
            Authentication auth
    ) {

        PaginationData data = new PaginationData(page, items, QuestionPageDtoDaoSortedByImpl.class.getSimpleName());
        data.getProps().put("user", auth.getPrincipal());
        data.getProps().put("dateFilter", dateFilter.getDay());
        data.getProps().put("sortedBy", questionViewSort);

        return new ResponseEntity<>(questionDtoService.getPageDto(data), HttpStatus.OK);
    }
}

