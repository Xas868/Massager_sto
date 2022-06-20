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
    private String image;
    private String chatName;
    private PageDTO<MessageDto> messages;
    private LocalDateTime persistDateTime;

    public GroupChatDto(long id, String image, String chatName, LocalDateTime persistDateTime) {
        this.id = id;
        this.image = image;
        this.chatName = chatName;
        this.persistDateTime = persistDateTime;
    }

    @Override
    public String toString() {
        return ("Group chat: id = " + id +
                ", image link = " + image +
                ", chat name = " + chatName +
                ", messages = " + messages +
                ", persist date = " + persistDateTime);
    }
}
