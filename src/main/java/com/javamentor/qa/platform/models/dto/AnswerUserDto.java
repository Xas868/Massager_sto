package com.javamentor.qa.platform.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AnswerUserDto {

    private Long answerId;
    private Long questionId;
    private Long countAnswerVote;
    private LocalDateTime persistDate;
    private String  htmlBody;

}
