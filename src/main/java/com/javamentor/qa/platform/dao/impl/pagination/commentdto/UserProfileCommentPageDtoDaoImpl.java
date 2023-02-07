package com.javamentor.qa.platform.dao.impl.pagination.commentdto;

import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;
import com.javamentor.qa.platform.models.dto.UserProfileCommentDto;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Repository("UserProfileCommentPageDtoDaoImpl")
public class UserProfileCommentPageDtoDaoImpl implements PageDtoDao<UserProfileCommentDto> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<UserProfileCommentDto> getPaginationItems(PaginationData properties) {
        int itemsOnPage = properties.getItemsOnPage();
        int offset = (properties.getCurrentPage() - 1) * itemsOnPage;
        User user = (User) properties.getProps().get("user");

        return entityManager.createQuery(
                        "SELECT NEW com.javamentor.qa.platform.models.dto.UserProfileCommentDto" +
                        "(c.id," +
                        "c.text," +
                        "c.persistDateTime," +
                        "(SELECT comA.answer.id FROM comA WHERE comA.comment.id = c.id)," +
                        "CASE WHEN c.commentType = 0 THEN answer.question.id ELSE question.id END," +
                        "c.commentType)" +
                        "FROM Comment c " +
                        "LEFT JOIN CommentQuestion comQ ON comQ.comment.id = c.id LEFT JOIN comQ.question as question " +
                        "LEFT JOIN CommentAnswer comA ON comA.comment.id = c.id left JOIN comA.answer as answer " +
                        "WHERE c.user.id = :id ", UserProfileCommentDto.class)
                .setParameter("id", user.getId())
                .setFirstResult(offset)
                .setMaxResults(itemsOnPage)
                .getResultList();
    }

    @Override
    public Long getTotalResultCount(Map<String, Object> properties) {
        return (Long) entityManager.createQuery("SELECT COUNT (c) FROM Comment c WHERE c.user.id = :id")
                .setParameter("id", properties.get("userId"))
                .getSingleResult();
    }
}
