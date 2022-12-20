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

    Long countVoteUp;
    Long countVoteDown;
    Long countVoteQuestion;
    Long countVoteAnswer;
    Long countVoteMonth;

}
