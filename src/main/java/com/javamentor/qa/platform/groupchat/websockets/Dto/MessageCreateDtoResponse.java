package com.javamentor.qa.platform.groupchat.websockets.Dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MessageCreateDtoResponse  implements Serializable {

    private Long message_id;
    private Long chat_id;
    private Long userSender_id;
    private String message;
    private LocalDateTime persistDate;

    private String nickname;
    private String imageLink;

    @Override
    public String toString() {
        return "MessageCreateDtoResponse{" +
                "message_id=" + message_id +
                ", chat_id=" + chat_id +
                ", userSender_id=" + userSender_id +
                ", bodyMessage='" + message + '\'' +
                ", creationDate=" + persistDate +
                ", nickname='" + nickname + '\'' +
                ", image_link='" + imageLink + '\'' +
                '}';
    }
}
