package com.javamentor.qa.platform.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileQuestionDto {
    private Long questionId;
    private String title;
    private List<TagDto> listTagDto;
    private Long countAnswer;
    private LocalDateTime persistDateTime;
    private long view;
    private long vote;

    public UserProfileQuestionDto(Long questionId, String title, Long countAnswer, LocalDateTime persistDateTime, long view, long vote) {
        this.questionId = questionId;
        this.title = title;
        this.countAnswer = countAnswer;
        this.persistDateTime = persistDateTime;
        this.view = view;
        this.vote = vote;
    }
}
