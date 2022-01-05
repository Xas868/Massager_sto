package com.javamentor.qa.platform.webapp.converters;

import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class QuestionDTOToQuestionConverter {


//    @Mapping(source = "question.id", target = "id")
//    @Mapping(source = "question.user.id", target = "authorId")
    @Mapping(source = "question.title", target = "title")
    @Mapping(source = "question.description", target = "description")
//    @Mapping(source = "question.user.fullName", target = "authorName")
//    @Mapping(source = "question.user.imageLink", target = "authorImage")
//    @Mapping(expression = "java(this.getViewsAmount(question))", target = "viewCount")
    @Mapping(source = "question.tags", target = "listTagDto")
//    @Mapping(source = "question.persistDateTime", target = "persistDateTime")
//    @Mapping(source = "question.lastUpdateDateTime", target = "lastUpdateDateTime")
//    @Mapping(target = "countValuable", constant = "0")
    public abstract Question questionDTOToQuestion(QuestionDto questionDto);

}
