package com.javamentor.qa.platform.webapp.converters;

import com.javamentor.qa.platform.models.dto.AnswerDTO;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public abstract class AnswerConverter {

    @Mapping(source = "dateAccept", target = "dateAcceptTime")
    @Mapping(constant = "false", target = "isDeleted")
    @Mapping(constant = "false", target = "isDeletedByModerator")
    public abstract Answer answerDTOToAnswer  (AnswerDTO answerDTO);

}
