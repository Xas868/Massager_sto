package com.javamentor.qa.platform.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileTagDto {
    private long id;
    private String tagName;
    private Long countVoteTag;
    private Long countAnswerQuestion;
}
