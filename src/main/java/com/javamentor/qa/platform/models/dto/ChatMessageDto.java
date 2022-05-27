package com.javamentor.qa.platform.models.dto;

import com.javamentor.qa.platform.models.entity.chat.Chat;
import com.javamentor.qa.platform.models.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDto {

    private Long message_id;
    private Long chat_id;
    private Long userSender_id;
    private String bodyMessage;

    private String nickname;
    private String image_link;


}



