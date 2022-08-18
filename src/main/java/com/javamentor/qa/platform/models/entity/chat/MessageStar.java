package com.javamentor.qa.platform.models.entity.chat;

import com.javamentor.qa.platform.models.entity.user.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Table(name = "message_star")
public class MessageStar {

    @Id
    @GeneratedValue(generator = "Message_star_seq")
    private Long id;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Message message;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    @Column(name = "persist_date", updatable = false)
    @CreationTimestamp
    @Type(type = "org.hibernate.type.LocalDateTimeType")
    private LocalDateTime persistDateTime;
}
