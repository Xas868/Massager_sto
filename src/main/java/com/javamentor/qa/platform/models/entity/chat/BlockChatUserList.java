package com.javamentor.qa.platform.models.entity.chat;

import com.javamentor.qa.platform.models.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "block_chat_user_list")
public class BlockChatUserList {

    @Id
    @GeneratedValue(generator = "block_chat_user_list_seq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User profile;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User blocked;

    @Column(name = "persist_date", nullable = false, updatable = false)
    @Type(type = "org.hibernate.type.LocalDateTimeType")
    @CreationTimestamp
    private LocalDateTime persistDate;

}
