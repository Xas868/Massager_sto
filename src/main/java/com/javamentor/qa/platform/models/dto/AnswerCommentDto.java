package com.javamentor.qa.platform.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnswerCommentDto {

    private Long id;
    private Long answerId;
    private LocalDateTime lastRedactionDate;
    private LocalDateTime persistDate;
    private String text;
    private Long userId;
    private String imageLink;
    private Long reputation;

}
