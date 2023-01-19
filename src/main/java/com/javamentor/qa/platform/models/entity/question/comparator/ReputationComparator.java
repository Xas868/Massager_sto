package com.javamentor.qa.platform.models.entity.question.comparator;

import com.javamentor.qa.platform.models.dto.UserProfileReputationDto;

import java.util.Comparator;

public class ReputationComparator implements Comparator<UserProfileReputationDto> {
    @Override
    public int compare(UserProfileReputationDto o1, UserProfileReputationDto o2) {
        return o2.getPersistDate().compareTo(o1.getPersistDate());
    }
}
