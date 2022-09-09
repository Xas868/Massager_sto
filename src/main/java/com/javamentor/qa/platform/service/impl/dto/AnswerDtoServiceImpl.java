package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.AnswerDtoDao;
import com.javamentor.qa.platform.dao.abstracts.model.VoteAnswerDao;
import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;
import com.javamentor.qa.platform.models.dto.AnswerCommentDto;
import com.javamentor.qa.platform.models.dto.AnswerDTO;
import com.javamentor.qa.platform.models.dto.AnswerUserDto;
import com.javamentor.qa.platform.service.abstracts.dto.AnswerDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnswerDtoServiceImpl extends DtoServiceImpl<AnswerDTO> implements AnswerDtoService {


    private final AnswerDtoDao answerDtoDao;
    public final VoteAnswerDao voteAnswerDao;

    @Autowired
    public AnswerDtoServiceImpl(Map<String, PageDtoDao<AnswerDTO>> daoMap, AnswerDtoDao answerDtoDao, VoteAnswerDao voteAnswerDao) {
        super(daoMap);
        this.answerDtoDao = answerDtoDao;
        this.voteAnswerDao = voteAnswerDao;
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
        List<AnswerDTO> answers = answerDtoDao.getAllUndeletedAnswerDtoByQuestionId(questionId);
        Map<AnswerDTO, Long> answersAndHisVotes = new HashMap<>();

        for (AnswerDTO answer : answers) {
            answersAndHisVotes.put(answer, voteAnswerDao.getVoteCount(answer.getId()));
        }
        Map<AnswerDTO, Long> sortedAnswersAndHisVotes = sortAnswersAndHisVotesMap(answersAndHisVotes);
        List<AnswerDTO> sortedAnswers = getAnswersListFromSortedMap(sortedAnswersAndHisVotes);
        setComment(sortedAnswers);

        return sortedAnswers;
    }

    @Override
    public List<AnswerCommentDto> getAllCommentsDtoByAnswerId(Long answerId) {

        return answerDtoDao.getAllCommentsDtoByAnswerId(answerId);
    }

    public Map<AnswerDTO, Long> sortAnswersAndHisVotesMap(Map<AnswerDTO, Long> map) {

        return map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors
                        .toMap(Map.Entry::getKey,
                                Map.Entry::getValue,
                                (e1, e2) -> e1,
                                LinkedHashMap::new));
    }

    public List<AnswerDTO> getAnswersListFromSortedMap(Map<AnswerDTO, Long> sortedMap) {
        List<AnswerDTO> resultList = new ArrayList<>(sortedMap.keySet());
        Collections.reverse(resultList);
        AnswerDTO bestAnswer = null;

        for (AnswerDTO answer : resultList) {
            if (Boolean.TRUE.equals(answer.getIsHelpful())) {
                bestAnswer = answer;
            }
        }

        if (bestAnswer != null) {
            resultList.remove(bestAnswer);
            resultList.add(0, bestAnswer);
        }

        return resultList;
    }

    public void setComment(List<AnswerDTO> answers) {
        for (AnswerDTO answer : answers) {
            answer.setCommentOnTheAnswerToTheQuestion(getAllCommentsDtoByAnswerId(answer.getId()));
        }
    }


    @Override
    public List<AnswerUserDto> getLastAnswersForWeek(Long userId) {
        return answerDtoDao.getLastAnswersForWeek(userId);
    }
}

