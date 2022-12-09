package com.javamentor.qa.platform.models.entity.question.comparator;

import com.javamentor.qa.platform.models.dto.UserProfileQuestionDto;
import java.util.Comparator;

public class DateComparator implements Comparator<UserProfileQuestionDto> {
    @Override
    public int compare(UserProfileQuestionDto o1, UserProfileQuestionDto o2) {
        return o2.getPersistDateTime().compareTo(o1.getPersistDateTime());
    }
}