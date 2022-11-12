package com.javamentor.qa.platform.models.dto.question;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionCommentDto {
    private Long id;
    private Long questionId;
    private LocalDateTime lastRedactionDate;
    private LocalDateTime persistDate;
    private String text;
    private Long userId;
    private String imageLink;
    private Long reputation;

    public QuestionCommentDto(Long id, Long questionId, LocalDateTime lastRedactionDate, LocalDateTime persistDate, String text, Long userId, String imageLink, Integer reputation) {
        this.id = id;
        this.questionId = questionId;
        this.lastRedactionDate = lastRedactionDate;
        this.persistDate = persistDate;
        this.text = text;
        this.userId = userId;
        this.imageLink = imageLink;
        this.reputation = Long.valueOf(reputation);
    }
}
