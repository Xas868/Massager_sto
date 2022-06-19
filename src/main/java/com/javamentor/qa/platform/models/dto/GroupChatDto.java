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
public class GroupChatDto {
    private long id;
    private String chatName;
    private PageDTO<MessageDto> messages;
    private LocalDateTime persistDateTime;
    //TODO должна быть картинка чата. Пока что неясно, откуда она должна взяться.
//    private String image;

    public GroupChatDto(long id, String chatName, LocalDateTime persistDateTime) {
        this.id = id;
        this.chatName = chatName;
        this.persistDateTime = persistDateTime;
    }

    @Override
    public String toString() {
        return ("Group chat: id = " + id + ", chat name = " + chatName + ", messages = " + messages + ", persist date = " + persistDateTime);
    }
}
