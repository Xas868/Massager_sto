package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.BookMarksDtoDao;
import com.javamentor.qa.platform.models.dto.BookMarksDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class BookMarksDtoDaoImpl implements BookMarksDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<BookMarksDto> getAllBookMarksInUserProfile(Long id) {
        return entityManager.createQuery("select new com.javamentor.qa.platform.models.dto.BookMarksDto (" +
                        "b.question.id," +
                        "b.question.title ," +
                        "(select count(a.id) from Answer a where a.question.id = b.question.id)," +
                        "(select sum(case when v.vote = 'UP_VOTE' then 1 else -1 end) from VoteQuestion v where v.question.id = b.question.id)," +
                        "(select count(qv.id) FROM QuestionViewed qv where qv.question.id = b.question.id)," +
                        "b.question.persistDateTime," +
                        "b.note) " +
                        "from BookMarks b where b.question.isDeleted=false and b.user.id =: id", BookMarksDto.class)
                .setParameter("id", id)
                .getResultList();
    }
}
