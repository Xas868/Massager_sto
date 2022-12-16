package com.javamentor.qa.platform.models.entity.question.answer;

import com.javamentor.qa.platform.models.dto.UserProfileAnswerDto;
import com.javamentor.qa.platform.models.entity.question.answer.comparator.AnswerDateComparator;
import com.javamentor.qa.platform.models.entity.question.answer.comparator.AnswerVoteComparator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Comparator;
@Getter
@AllArgsConstructor
public enum ProfileAnswerSort {
    VOTE(new AnswerVoteComparator(), "order by sVote desc"),
    NEW(new AnswerDateComparator(), "order by a.persistDateTime desc");
    private Comparator<UserProfileAnswerDto> comparator;
    private String comparingField;
}