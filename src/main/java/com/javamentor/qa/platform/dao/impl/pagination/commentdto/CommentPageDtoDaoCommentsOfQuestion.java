package com.javamentor.qa.platform.dao.impl.pagination.commentdto;


import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;
import com.javamentor.qa.platform.models.dto.question.QuestionCommentDto;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Repository("CommentPageDtoDaoCommentsOfQuestion")
public class CommentPageDtoDaoCommentsOfQuestion implements PageDtoDao<QuestionCommentDto> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<QuestionCommentDto> getPaginationItems(PaginationData properties) {
        int itemsOnPage = properties.getItemsOnPage();
        int firstResultOffset = (properties.getCurrentPage() - 1) * itemsOnPage;
        return entityManager.createQuery("select new com.javamentor.qa.platform.models.dto.question.QuestionCommentDto(" +
                        "c.comment.id, " +
                        "c.question.id, " +
                        "c.comment.lastUpdateDateTime, " +
                        "c.comment.persistDateTime, " +
                        "c.comment.text, " +
                        "c.comment.user.id, " +
                        "c.comment.user.imageLink, " +
                        "(coalesce((select sum(r.count) from Reputation r where r.question.id=c.question.id), 0))) " +
                        "from CommentQuestion as c where c.question.id = :questionId and c.comment.commentType=1 " +
                        "order by c.comment.persistDateTime desc", QuestionCommentDto.class)
                .setParameter("questionId", properties.getProps().get("questionId"))
                .setFirstResult(firstResultOffset)
                .setMaxResults(itemsOnPage)
                .getResultList();
    }

    @Override
    public Long getTotalResultCount(Map<String, Object> properties) {
        return (long) (entityManager.createQuery("select count (c.id) " +
                        "from CommentQuestion as c " +
                        "where c.question.id = :questionId ")
                .setParameter("questionId", properties.get("questionId"))
                .getSingleResult());
    }
}
