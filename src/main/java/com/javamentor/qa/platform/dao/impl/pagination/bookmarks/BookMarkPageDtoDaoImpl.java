package com.javamentor.qa.platform.dao.impl.pagination.bookmarks;

import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;
import com.javamentor.qa.platform.models.dto.BookMarksDto;
import com.javamentor.qa.platform.models.entity.bookmark.SortBookmark;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Repository("BookMarkPageDtoDaoImpl")
public class BookMarkPageDtoDaoImpl implements PageDtoDao<BookMarksDto> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<BookMarksDto> getPaginationItems(PaginationData properties) {
        int itemsOnPage = properties.getItemsOnPage();
        int offset = (properties.getCurrentPage() - 1) * itemsOnPage;
        User user = (User) properties.getProps().get("user");
        SortBookmark sortBookmark = (SortBookmark) properties.getProps().get("sortBookmark");
        Long groupId = (Long) properties.getProps().get("groupId");

        return entityManager.createQuery(
                        "SELECT NEW com.javamentor.qa.platform.models.dto.BookMarksDto (" +
                        "b.id," +
                        "gb.title," +
                        "b.question.id," +
                        "b.question.title ," +
                        "(SELECT COUNT(a.id) FROM Answer a WHERE a.question.id = b.question.id)," +
                        "(SELECT SUM(CASE WHEN v.vote = 'UP_VOTE' THEN 1 ELSE -1 END) FROM VoteQuestion v WHERE v.question.id = b.question.id) AS qVoted," +
                        "(SELECT COUNT(qv.id) FROM QuestionViewed qv WHERE qv.question.id = b.question.id) as qViewed," +
                        "b.question.persistDateTime," +
                        "b.note) " +
                        "FROM BookMarks b, GroupBookmark gb LEFT JOIN gb.bookMarks as gb_Bookmarks " +
                        "WHERE b.id = gb_Bookmarks.id AND (gb.id = :groupId OR :groupId = null) AND b.question.isDeleted = FALSE " +
                        "AND b.user.id = :id ORDER BY " + sortBookmark.getComparingField(), BookMarksDto.class)
                .setParameter("id", user.getId())
                .setParameter("groupId", groupId)
                .setFirstResult(offset)
                .setMaxResults(itemsOnPage)
                .getResultList();
    }

    @Override
    public Long getTotalResultCount(Map<String, Object> properties) {
        return (long) entityManager.createQuery(
                        "select count (b) from BookMarks b where b.user.id = :id")
                .setParameter("id", properties.get("userId"))
                .getSingleResult();
    }
}
