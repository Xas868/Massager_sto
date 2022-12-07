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
public class BookMarksDto {
    private Long questionId;
    private String title;
    private List<TagDto> listTagDto;
    private Long countAnswer;
    private Long countVote;
    private Long countView;
    private LocalDateTime persistDateTime;
    private String note;

    public BookMarksDto(Long questionId, String title, Long countAnswer, Long countVote, Long countView, LocalDateTime persistDateTime, String note) {
        this.questionId = questionId;
        this.title = title;
        this.countAnswer = countAnswer;
        this.countVote = countVote;
        this.countView = countView;
        this.persistDateTime = persistDateTime;
        this.note = note;
    }
}

