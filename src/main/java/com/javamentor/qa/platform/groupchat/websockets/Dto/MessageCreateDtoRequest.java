package com.javamentor.qa.platform.groupchat.websockets.Dto;


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




    @Override
    public String toString() {
        return "MessageCreateDtoRequest{" +



                ", bodyMessage='" + message + '\'' +
                '}';
    }
}
