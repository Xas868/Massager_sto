package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.BookmarksDao;
import com.javamentor.qa.platform.models.entity.BookMarks;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class BookmarksDaoImpl extends ReadWriteDaoImpl<BookMarks, Long> implements BookmarksDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean findBookmarksByUserAndQuestion(Long userId, Long questionId) {
        return entityManager
                .createQuery("select count(b) from BookMarks b where b.user.id =:userId and b.question.id =:questionId", Long.class)
                .setParameter("userId", userId)
                .setParameter("questionId", questionId)
                .getSingleResult() == 0;
    }

    @Override
    public List<BookMarks> getAllBookMarksByUserId(Long id) {
        return entityManager.createQuery("select new BookMarks (" +
                        "b.id," +
                        "b.user," +
                        "b.question," +
                        "b.note" +
                        ")" +
                        "from BookMarks b where b.user.id = :id", BookMarks.class)
                .setParameter("id", id)
                .getResultList();
    }
}
