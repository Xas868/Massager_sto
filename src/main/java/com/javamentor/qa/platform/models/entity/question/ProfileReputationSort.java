package com.javamentor.qa.platform.models.entity.question;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Getter
public enum ProfileReputationSort {
    NEW {@Override public String ComparingField() { return "a.persistDate desc"; }};

    public abstract String ComparingField();
}
