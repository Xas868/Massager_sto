package com.javamentor.qa.platform.webapp.controllers.rest;



import com.javamentor.qa.platform.models.dto.QuestionResponce;
import com.javamentor.qa.platform.service.abstracts.dto.QuestionDtoService;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User question information", description = "Информация по вопросу пользователя")
public class QuestionResourceController {

    public final QuestionDtoService questionDtoService;
    public final QuestionService questionService;

    public QuestionResourceController(QuestionDtoService questionDtoService, QuestionService questionService) {
        this.questionDtoService = questionDtoService;
        this.questionService = questionService;
    }

    @GetMapping("/question/{id}")
    @Operation(summary = "Получение информации по вопросу пользователя")
    @ApiResponse(responseCode = "200", description = "Информация по вопросу", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = QuestionResponce.class))
    })
    @ApiResponse(responseCode = "500", description = "Не верный номер вопроса", content = {
            @Content(mediaType = "application/json")
    })
    public ResponseEntity<Object> getQuestion(@PathVariable Long id){
        if (questionService.isQuestionValidate(id)) return new ResponseEntity<>("Can't find question with id:"+ id , HttpStatus.NOT_FOUND); //+
        return new ResponseEntity<>(questionDtoService.getQuestionDtoServiceById(id), HttpStatus.OK);
    }
}
