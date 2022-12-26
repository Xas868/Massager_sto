package com.javamentor.qa.platform.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileVoteDto {

    private Long countVoteUp;
    private Long countVoteDown;
    private Long countVoteQuestion;
    private Long countVoteAnswer;
    private Long countVoteMonth;

}
