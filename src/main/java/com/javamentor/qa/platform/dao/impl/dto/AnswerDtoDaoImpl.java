package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.AnswerDtoDao;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.dto.AnswerDTO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class AnswerDtoDaoImpl implements AnswerDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<AnswerDTO> getUndeletedAnswerDtoById(Long id) {

        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery("select new com.javamentor.qa.platform.models.dto.AnswerDTO(" +
                        "a.id, a.user.id, (select sum(r.count) from Reputation r where r.author.id = a.user.id), " +
                        "a.question.id, a.htmlBody, a.persistDateTime, a.isHelpful, a.dateAcceptTime, " +
                        "(select coalesce(sum(case when v.vote = 'UP_VOTE' then 1 else -1 end), 0) from VoteAnswer v where v.answer.id = a.id)," +
                        "a.user.imageLink, a.user.nickname) from Answer as a where a.id = :id and a.isDeleted = false", AnswerDTO.class)
                .setParameter("id", id));
    }

    @Override
    public List<AnswerDTO> getAllUndeletedAnswerDtoByQuestionId(Long questionId) {

        return entityManager.createQuery("select new com.javamentor.qa.platform.models.dto.AnswerDTO( a.id, a.user.id, " +
                        "(select sum(r.count) from Reputation r where r.author.id = a.user.id), " +
                        "a.question.id, a.htmlBody, a.persistDateTime, a.isHelpful, a.dateAcceptTime, " +
                        "(select coalesce(sum(case when v.vote = 'UP_VOTE' then 1 else -1 end), 0) from VoteAnswer v " +
                        "where v.answer.id = a.id), a.user.imageLink, a.user.nickname) " +
                        "from Answer as a where a.question.id = :id and a.isDeleted = false order by a.id", AnswerDTO.class)
                .setParameter("id", questionId)
                .getResultList();
    }
}
