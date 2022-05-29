package com.javamentor.qa.platform.groupchat.websockets.Dto;


import com.javamentor.qa.platform.models.entity.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MessageCreateDtoRequest implements Serializable {


    @Schema(description = "Тело сообщения")
    private String message;
    private User userSender;
    private String time;


    private Long chat_id;





    @Override
    public String toString() {
        return "MessageCreateDtoRequest{" +



                ", bodyMessage='" + message + '\'' +
                '}';
    }
}
