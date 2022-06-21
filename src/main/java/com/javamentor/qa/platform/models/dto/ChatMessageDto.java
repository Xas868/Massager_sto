package com.javamentor.qa.platform.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDto {

    private Long messageId;
    private Long chatId;
    private Long userSenderId;
    private String bodyMessage;

    private String nickname;
    private String imageLink;


}



