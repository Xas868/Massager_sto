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
public class AnswerDTO {

    private Long id;
    private Long userId; //+++
    private Long userReputation; //---
    private Long questionId; //+++
    private String htmlBody;
    private LocalDateTime persistDateTime;
    private Boolean isHelpful;
    private Boolean isUserVote;
    private LocalDateTime dateAccept; //+++
    private Long countValuable; //---
    private String image; //---
    private String nickName;
    private List<AnswerCommentDto> commentOnTheAnswerToTheQuestion;

    public AnswerDTO(Long id, Long userId, Long userReputation, Long questionId, String htmlBody,
                     LocalDateTime persistDateTime, Boolean isHelpful, LocalDateTime dateAccept,
                     Boolean isUserVote, String image, String nickName) {
        this.id = id;
        this.userId = userId;
        this.userReputation = userReputation;
        this.questionId = questionId;
        this.htmlBody = htmlBody;
        this.persistDateTime = persistDateTime;
        this.isHelpful = isHelpful;
        this.isUserVote = isUserVote;
        this.dateAccept = dateAccept;
        this.image = image;
        this.nickName = nickName;

    }

    public AnswerDTO(Long id, Long userId, Long userReputation, Long questionId, String htmlBody,
                     LocalDateTime persistDateTime, Boolean isHelpful,
                     LocalDateTime dateAccept, Long countValuable, String image, String nickName) {
        this.id = id;
        this.userId = userId;
        this.userReputation = userReputation;
        this.questionId = questionId;
        this.htmlBody = htmlBody;
        this.persistDateTime = persistDateTime;
        this.isHelpful = isHelpful;
        this.dateAccept = dateAccept;
        this.countValuable = countValuable;
        this.image = image;
        this.nickName = nickName;

    }


    //    private LocalDateTime updateDateTime;--------------
//    private Boolean isDeleted;
//    private Boolean isDeletedByModerator;

}
