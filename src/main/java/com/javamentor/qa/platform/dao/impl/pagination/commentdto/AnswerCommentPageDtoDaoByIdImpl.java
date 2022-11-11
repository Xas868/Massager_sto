package com.javamentor.qa.platform.dao.impl.pagination.commentdto;

import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;
import com.javamentor.qa.platform.models.dto.CommentDto;
import com.javamentor.qa.platform.models.entity.CommentType;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Repository("AnswerCommentPageDtoDaoByIdImpl")
public class AnswerCommentPageDtoDaoByIdImpl implements PageDtoDao<CommentDto> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<CommentDto> getPaginationItems(PaginationData properties) {
        int itemsOnPage = properties.getItemsOnPage();
        int offset = (properties.getCurrentPage() - 1) * itemsOnPage;
        return entityManager
                .createQuery("SELECT new com.javamentor.qa.platform.models.dto.CommentDto( " +
                            "c.id, c.lastUpdateDateTime, c.persistDateTime, c.text, c.user.id, " +
                            "(SELECT u.imageLink FROM User u where u.id = c.user.id), " +
                            "(SELECT sum(r.count) FROM Reputation r where r.author = c.user) ) " +
                            "FROM Comment as c " +
                            "JOIN CommentAnswer as ca on c.id = ca.comment.id " +
                            "where ca.answer.id = :answerId and c.commentType = :commentType " +
                            "order by c.persistDateTime", CommentDto.class)
                .setParameter("answerId", properties.getProps().get("answerId"))
                .setParameter("commentType", CommentType.ANSWER)
                .setFirstResult(offset)
                .setMaxResults(itemsOnPage)
                .getResultList();
    }

    @Override
    public Long getTotalResultCount(Map<String, Object> properties) {
        return (Long) entityManager
                .createQuery("SELECT count(c.id) FROM Comment c " +
                            "JOIN CommentAnswer as ca on c.id = ca.comment.id " +
                            "where ca.answer.id = :answerId and c.commentType = :commentType")
                .setParameter("answerId", properties.get("answerId"))
                .setParameter("commentType", CommentType.ANSWER)
                .getSingleResult();
    }

    @Override
    public String toString() {
        return getClass().getName();
    }
}
