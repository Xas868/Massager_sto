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

@Repository("CommentPageDtoDaoCommentsOfQuestion")
public class CommentPageDtoDaoCommentsOfQuestion implements PageDtoDao<CommentDto> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<CommentDto> getPaginationItems(PaginationData properties) {
        int itemsOnPage = properties.getItemsOnPage();
        int firstResultOffset = (properties.getCurrentPage() - 1) * itemsOnPage;
        return entityManager.createQuery("select new com.javamentor.qa.platform.models.dto.CommentDto(" +
                        "c.id, c.lastUpdateDateTime, c.persistDateTime, c.text, c.user.id, " +
                        "(SELECT u.imageLink FROM User u where u.id = c.user.id), " +
                        "(coalesce((select sum (r.count) FROM Reputation r where r.author = c.user), 0))) " +
                        "FROM Comment as c " +
                        "JOIN CommentQuestion as cq on c.id = cq.comment.id " +
                        "where cq.question.id = :questionId and c.commentType=:commentType " +
                        "order by c.persistDateTime desc", CommentDto.class)
                .setParameter("questionId", properties.getProps().get("questionId"))
                .setParameter("commentType", CommentType.QUESTION)
                .setFirstResult(firstResultOffset)
                .setMaxResults(itemsOnPage)
                .getResultList();
    }

    @Override
    public Long getTotalResultCount(Map<String, Object> properties) {
        return (Long) (entityManager.createQuery("select count (c.id) " +
                        "from Comment c " +
                        "JOIN CommentQuestion as cq on c.id = cq.comment.id " +
                        "where cq.question.id = :questionId and c.commentType=:commentType")
                .setParameter("questionId", properties.get("questionId"))
                .setParameter("commentType", CommentType.QUESTION)
                .getSingleResult());
    }
}
