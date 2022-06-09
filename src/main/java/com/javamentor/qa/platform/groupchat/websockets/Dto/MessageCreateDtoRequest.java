package com.javamentor.qa.platform.groupchat.websockets.Dto;


import com.javamentor.qa.platform.models.entity.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MessageCreateDtoRequest implements Serializable {


    @Schema(description = "Тело сообщения")
    private String message;
    private Long senderId;
    private LocalDateTime time;
    private Long chatId;
    private String senderNickname;
    private String senderImage;




    @Override
    public String toString() {
        return "MessageCreateDtoRequest{" +



                ", bodyMessage='" + message + '\'' +
                '}';
    }
}
