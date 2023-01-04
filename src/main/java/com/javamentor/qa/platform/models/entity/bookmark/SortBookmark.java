package com.javamentor.qa.platform.models.entity.bookmark;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SortBookmark {
    NEW("b.question.persistDateTime desc"),
    VIEW("qViewed desc"),
    VOTE("qVoted desc NULLS LAST");

    private final String comparingField;
}
