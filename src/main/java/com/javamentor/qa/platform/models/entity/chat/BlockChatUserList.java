package com.javamentor.qa.platform.models.entity.chat;

import com.javamentor.qa.platform.models.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.Objects;

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

    public BlockChatUserList(User profile, User blocked, LocalDateTime persistDate) {
        this.profile = profile;
        this.blocked = blocked;
        this.persistDate = persistDate;
    }

    @Column(name = "persist_date", nullable = false, updatable = false)
    @Type(type = "org.hibernate.type.LocalDateTimeType")
    @CreationTimestamp
    private LocalDateTime persistDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlockChatUserList that = (BlockChatUserList) o;
        return Objects.equals(id, that.id) && Objects.equals(profile, that.profile) && Objects.equals(blocked, that.blocked) && Objects.equals(persistDate, that.persistDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, profile, blocked, persistDate);
    }
}
