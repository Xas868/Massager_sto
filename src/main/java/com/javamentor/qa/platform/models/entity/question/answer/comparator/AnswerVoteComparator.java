package com.javamentor.qa.platform.models.entity.question.answer.comparator;

import com.javamentor.qa.platform.models.dto.UserProfileAnswerDto;
import java.util.Comparator;

public class AnswerVoteComparator implements Comparator<UserProfileAnswerDto> {
    @Override
    public int compare(UserProfileAnswerDto o1, UserProfileAnswerDto o2) {
        return Long.compare(o2.getVote(), o1.getVote());
    }
}