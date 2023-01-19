package com.javamentor.qa.platform.models.dto;

import com.javamentor.qa.platform.models.entity.user.reputation.ReputationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor

public class UserProfileReputationDto {
    private int countReputation; // это не общие количество, а просто значение репутация
    private String questionTitle;
    private Long questionId;
    private Long answerId; // данное поле может быть null если репутация засчитана не за ответ
    private LocalDateTime persistDate;
    private ReputationType reputationType;  //это enum показывающий за что получил пользователь репутацию

//    public UserProfileReputationDto(int countReputation, String questionTitle, Long questionId, Long answerId, LocalDateTime persistDate, Integer reputationType) {
//        this.countReputation = countReputation;
//        this.questionTitle = questionTitle;
//        this.questionId = questionId;
//        this.answerId = answerId;
//        this.persistDate = persistDate;
//        this.reputationType = ReputationType.fromIntegerToEnum(reputationType);
//    }
//    public UserProfileReputationDto(int countReputation, String questionTitle, Long questionId, Long answerId, LocalDateTime persistDate, ReputationType reputationType) {
//        this.countReputation = countReputation;
//        this.questionTitle = questionTitle;
//        this.questionId = questionId;
//        this.answerId = answerId;
//        this.persistDate = persistDate;
//        this.reputationType = reputationType;
//    }

    public UserProfileReputationDto(int countReputation, Long questionId, String questionTitle, Long answerId, LocalDateTime persistDate, ReputationType reputationType) {
        this.countReputation = countReputation;
        this.questionTitle = questionTitle;
        this.questionId = questionId;
        this.answerId = answerId;
        this.persistDate = persistDate;
        this.reputationType = reputationType;
    }
}

