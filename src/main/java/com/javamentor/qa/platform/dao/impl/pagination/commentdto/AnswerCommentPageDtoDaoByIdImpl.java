package com.javamentor.qa.platform.dao.impl.pagination.commentdto;

import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;
import com.javamentor.qa.platform.models.dto.AnswerCommentDto;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Repository("AnswerCommentPageDtoDaoByIdImpl")
public class AnswerCommentPageDtoDaoByIdImpl implements PageDtoDao<AnswerCommentDto> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<AnswerCommentDto> getPaginationItems(PaginationData properties) {
        int itemsOnPage = properties.getItemsOnPage();
        int offset = (properties.getCurrentPage() - 1) * itemsOnPage;
        return entityManager
                .createQuery("SELECT new com.javamentor.qa.platform.models.dto.AnswerCommentDto(" +
                        "c.id, (SELECT ca.answer.id FROM CommentAnswer ca where ca.comment.id = c.id)," +
                        "c.lastUpdateDateTime, c.persistDateTime, c.text, c.user.id, c.user.imageLink, " +
                        "(SELECT sum(r.count) FROM Reputation r where r.answer.id = :answerId)) " +
                        "FROM Comment as c " +
                        "JOIN CommentAnswer as ca on c.id = ca.comment.id " +
                        "where ca.answer.id = :answerId and c.commentType = 1 " +
                        "order by c.persistDateTime", AnswerCommentDto.class)
                .setParameter("answerId", properties.getProps().get("answerId"))
                .setFirstResult(offset)
                .setMaxResults(itemsOnPage)
                .getResultList();
    }

    @Override
    public Long getTotalResultCount(Map<String, Object> properties) {
        return (Long) entityManager.createQuery("SELECT count(c.id) FROM Comment c JOIN CommentAnswer as ca on c.id = ca.comment.id where ca.answer.id = :answerId and c.commentType = 1")
                .setParameter("answerId", properties.get("answerId"))
                .getSingleResult();
    }

    @Override
    public String toString() {
        return getClass().getName();
    }
}
