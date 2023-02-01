package com.javamentor.qa.platform.models.entity;

import com.javamentor.qa.platform.models.entity.bookmark.BookMarks;
import com.javamentor.qa.platform.models.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "group_bookmark")
public class GroupBookmark {
    @Id
    @GeneratedValue(generator = "group_bookmark_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    private String title;
    @ManyToMany(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinTable(name = "bookmark_has_group")
    private Set<BookMarks> bookMarks;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ToString.Exclude
    private User user;

    public GroupBookmark(String title, User user) {
        this.title = title;
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        GroupBookmark that = (GroupBookmark) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
