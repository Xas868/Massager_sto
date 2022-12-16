package com.javamentor.qa.platform.models.entity.question;

import com.javamentor.qa.platform.models.dto.UserProfileQuestionDto;
import com.javamentor.qa.platform.models.entity.question.comparator.DateComparator;
import com.javamentor.qa.platform.models.entity.question.comparator.ViewComparator;
import com.javamentor.qa.platform.models.entity.question.comparator.VoteComparator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;

@RequiredArgsConstructor
@Getter
@AllArgsConstructor
public enum ProfileQuestionSort {
    NEW(new DateComparator()),
    VIEW(new ViewComparator()),
    VOTE(new VoteComparator());
    private Comparator<UserProfileQuestionDto> comparator;
}