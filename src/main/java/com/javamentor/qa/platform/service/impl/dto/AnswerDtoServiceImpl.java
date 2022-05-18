package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.AnswerDtoDao;
import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;
import com.javamentor.qa.platform.models.dto.AnswerCommentDto;
import com.javamentor.qa.platform.models.dto.AnswerDTO;
import com.javamentor.qa.platform.service.abstracts.dto.AnswerDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AnswerDtoServiceImpl extends DtoServiceImpl<AnswerDTO> implements AnswerDtoService {

    private final AnswerDtoDao answerDtoDao;

    @Autowired
    public AnswerDtoServiceImpl(Map<String, PageDtoDao<AnswerDTO>> daoMap, AnswerDtoDao answerDtoDao) {
        super(daoMap);
        this.answerDtoDao = answerDtoDao;
    }

    @Override
    public Optional<AnswerDTO> getUndeletedAnswerDtoById(Long id) {
        Optional<AnswerDTO> answerDTO = answerDtoDao.getUndeletedAnswerDtoById(id);
        if (answerDTO.isPresent()) {
            answerDTO.get().setCommentOnTheAnswerToTheQuestion(getAllCommentsDtoByAnswerId(id));
            return answerDTO;
        }

        return Optional.empty();
    }

    @Override
    public List<AnswerDTO> getAllUndeletedAnswerDtoByQuestionId(Long questionId) {
        return answerDtoDao.getAllUndeletedAnswerDtoByQuestionId(questionId);
    }

    @Override
    public List<AnswerCommentDto> getAllCommentsDtoByAnswerId(Long answerId) {
        return answerDtoDao.getAllCommentsDtoByAnswerId(answerId);
    }
}

