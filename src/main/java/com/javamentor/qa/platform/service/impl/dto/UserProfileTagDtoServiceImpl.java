package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.TagDtoDao;
import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.dto.UserProfileTagDto;
import com.javamentor.qa.platform.service.abstracts.dto.UserProfileTagDtoService;
import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserProfileTagDtoServiceImpl implements UserProfileTagDtoService {
    private final TagDtoDao tagDtoDao;
    private final QuestionService questionService;
    private final AnswerService answerService;

    public UserProfileTagDtoServiceImpl(TagDtoDao tagDtoDao, QuestionService questionService, AnswerService answerService) {
        this.tagDtoDao = tagDtoDao;
        this.questionService = questionService;
        this.answerService = answerService;
    }

    public Optional<List<UserProfileTagDto>> getAllUserProfileTagDtoByUserId(Long id) {
        List<TagDto> trackedTagsByUserId = tagDtoDao.getTrackedTagsByUserId(id);
        List<List<Long>> questionIds = trackedTagsByUserId.stream()
                .map(t -> questionService.getAllQuestionIdByTagId(t.getId()).orElse(new ArrayList<>()))
                .collect(Collectors.toList());

        List<Long> countOfAnswerVote = new ArrayList<>(questionIds.size());
        for (int i = 0; i < questionIds.size(); i++) {
            long voteCount = 0;
            for (int j = 0; j < questionIds.get(i).size(); j++) {
                voteCount += answerService.getCountOfAnswerVoteByQuestionId(questionIds.get(i).get(j)).orElse(0L);
            }
            countOfAnswerVote.add(voteCount);
        }

        List<Long> countOfVoteQuestions = new ArrayList<>();

        for (int i = 0; i < questionIds.size(); i++) {
            long voteCount = 0;
            for (int j = 0; j < questionIds.get(i).size(); j++) {
                voteCount += questionService.getCountOfVoteByQuestionId(questionIds.get(i).get(j));
            }
            countOfVoteQuestions.add(voteCount);
        }


        List<Long> countOfQuestionBelowTag = new ArrayList<>(questionIds.size());

        for (int i = 0; i < questionIds.size(); i++) {
            countOfQuestionBelowTag.add((long) questionIds.get(i).size());
        }

        List<Long> countOfAnswerBelowQuestionBelowTag = new ArrayList<>(questionIds.size());

        for (int i = 0; i < questionIds.size(); i++) {
            long count = 0;
            for (int j = 0; j < questionIds.get(i).size(); j++) {
                Long aLong = answerService.getCountOfAnswerToQuestionByQuestionId(questionIds.get(i).get(j)).orElse(0L);
                count += aLong;
            }
            countOfAnswerBelowQuestionBelowTag.add(count);
        }

        ArrayList<UserProfileTagDto> userProfileTagDtos = new ArrayList<>(trackedTagsByUserId.size());
        TagDto tagDto;


        for (int i = 0; i < trackedTagsByUserId.size(); i++) {
            tagDto = trackedTagsByUserId.get(i);
            userProfileTagDtos.add(
                    UserProfileTagDto.builder()
                            .tagName(tagDto.getName())
                            .countVoteTag(countOfVoteQuestions.get(i) + countOfAnswerVote.get(i))
                            .countAnswerQuestion(countOfQuestionBelowTag.get(i) + countOfAnswerBelowQuestionBelowTag.get(i))
                            .build()
            );
        }
        return Optional.of(userProfileTagDtos);
    }
}
