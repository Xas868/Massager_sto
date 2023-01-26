package com.javamentor.qa.platform.models.entity.question;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Getter
@AllArgsConstructor
public enum ProfileReputationSort {
    NEW("a.persistDate desc" ); // public String comparingField() { return "a.persistDate desc"; }

    private String comparingField;
}
