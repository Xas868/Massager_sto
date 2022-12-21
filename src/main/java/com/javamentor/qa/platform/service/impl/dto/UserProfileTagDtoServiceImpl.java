package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.TagDtoDao;
import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.dto.UserProfileTagDto;
import com.javamentor.qa.platform.service.abstracts.dto.UserProfileTagDtoService;
import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import org.springframework.stereotype.Service;

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

        List<Long> countOfVoteQuestions = questionIds.stream()
                .map(q -> q.stream()
                        .map(questionService::getCountOfVoteByQuestionId)
                        .mapToLong(v -> v.orElse(0L))
                        .sum()
                )
                .collect(Collectors.toList());


        List<Long> countOfQuestionBelowTag = questionIds.stream()
                .map(v -> (long) v.size())
                .collect(Collectors.toList());

        List<Long> countOfAnswerVote = questionIds.stream()
                .map(q -> q.stream()
                        .map(answerService::getCountOfAnswerVoteByQuestionId)
                        .mapToLong(v -> v.orElse(0L))
                        .sum()
                )
                .collect(Collectors.toList());

        List<Long> countOfAnswerBelowQuestionBelowTag = questionIds.stream()
                .map(v -> v.stream()
                        .map(answerService::getCountOfAnswerToQuestionByQuestionId)
                        .mapToLong(value -> value.orElse(0L))
                        .sum()
                ).collect(Collectors.toList());

        ArrayList<UserProfileTagDto> userProfileTagDtos = new ArrayList<>(trackedTagsByUserId.size());
        TagDto tagDto;

        for (int i = 0; i < trackedTagsByUserId.size(); i++) {
            tagDto = trackedTagsByUserId.get(i);
            userProfileTagDtos.add(
                    UserProfileTagDto.builder()
                            .id(tagDto.getId())
                            .tagName(tagDto.getName())
                            .countVoteTag(countOfVoteQuestions.get(i) + countOfAnswerVote.get(i))
                            .countAnswerQuestion(countOfQuestionBelowTag.get(i) + countOfAnswerBelowQuestionBelowTag.get(i))
                            .build()
            );
        }
        return Optional.of(userProfileTagDtos);
    }
}