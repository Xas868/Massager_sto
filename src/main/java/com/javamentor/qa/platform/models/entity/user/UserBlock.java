package com.javamentor.qa.platform.models.entity.user;

import com.javamentor.qa.platform.models.entity.chat.Chat;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;


    @Entity
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Table(name = "user_chat_pin")
    public class UserBlock {
        @Id
        @GeneratedValue(generator = "id")
        private Long id;

        @ManyToOne
        @JoinColumn(name = "user_id")
        private Chat profile;

        @ManyToOne
        @JoinColumn(name = "user_id")
        private User block;

        @Column(name = "persist_date", updatable = false)
        @Type(type = "org.hibernate.type.LocalDateTimeType")
        @CreationTimestamp
        private LocalDateTime persistDate;
    }


