package com.javamentor.qa.platform.models.entity.question;

import com.javamentor.qa.platform.models.dto.UserProfileReputationDto;
import com.javamentor.qa.platform.models.entity.question.comparator.ReputationComparator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;

@RequiredArgsConstructor
@Getter
@AllArgsConstructor
public enum ProfileReputationSort {
    NEW(new ReputationComparator(),"a.persistDate desc" );
    private Comparator<UserProfileReputationDto> comparator;
    private String comparingField;
}
