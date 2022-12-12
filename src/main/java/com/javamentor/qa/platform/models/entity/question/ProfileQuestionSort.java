package com.javamentor.qa.platform.models.entity.question;

import com.javamentor.qa.platform.models.dto.UserProfileQuestionDto;
import com.javamentor.qa.platform.models.entity.question.comparator.DateComparator;
import com.javamentor.qa.platform.models.entity.question.comparator.ViewComparator;
import com.javamentor.qa.platform.models.entity.question.comparator.VoteComparator;

import java.util.Comparator;

public enum ProfileQuestionSort {
    NEW(new DateComparator()),
    VIEW(new ViewComparator()),
    VOTE(new VoteComparator());
    private Comparator<UserProfileQuestionDto> comparator;

    ProfileQuestionSort(Comparator<UserProfileQuestionDto> comparator) {
        this.comparator = comparator;
    }

    public Comparator<UserProfileQuestionDto> getComparator() {
        return comparator;
    }
}