package com.javamentor.qa.platform.models.entity.question.comparator;

import com.javamentor.qa.platform.models.dto.UserProfileQuestionDto;

import java.util.Comparator;

public class ViewComparator implements Comparator<UserProfileQuestionDto> {
    @Override
    public int compare(UserProfileQuestionDto o1, UserProfileQuestionDto o2) {
        return Long.compare(o2.getView(), o1.getView());
    }
}
