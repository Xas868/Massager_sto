package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.UserDtoDao;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.dto.UserDto;
import com.javamentor.qa.platform.models.dto.UserProfileQuestionDto;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDtoDaoImpl implements UserDtoDao {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<UserProfileQuestionDto> getAllUserProfileQuestionDtoById(Long id) {
        return entityManager.createQuery("select  new com.javamentor.qa.platform.models.dto.UserProfileQuestionDto(" +
                "q.id,q.title," +
                "coalesce((select count(a.id) from Answer a where a.question.id=q.id),0),q.persistDateTime ) " +
                "from Question q where q.user.id=:id")
                .setParameter("id",id)
                .getResultList();
    }

    @Override
    public Optional<UserDto> findUserDto(Long id) {

        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery("SELECT NEW com.javamentor.qa.platform.models.dto.UserDto(" +
                        "u.id," +
                        "u.email," +
                        "u.fullName," +
                        "u.imageLink," +
                        "u.city," +
                        "(select sum(r.count) from Reputation r where r.author.id=u.id)) FROM User u where u.id=:id and u.isDeleted=false", UserDto.class)
                .setParameter("id", id));
    }

    @Override
    public List<UserProfileQuestionDto> getAllUserProfileQuestionDtoByUserIdWhereQuestionIsDeleted(Long id) {
        return (List<UserProfileQuestionDto>) entityManager.createQuery("select " +
                        "new com.javamentor.qa.platform.models.dto.UserProfileQuestionDto (" +
                        "q.id," +
                        "q.title ," +
                        "coalesce((select count(a.id) from Answer a where a.question.id = q.id),0), " +
                        "q.persistDateTime) " +
                        "from Question q  where q.isDeleted=true and q.user.id=:id")
                .setParameter("id", id)
                .getResultList();
    }

    @Override
    public List<UserDto> getTop10UsersForPeriodRankedByNumberOfQuestions(Integer days) {
        return entityManager.createNativeQuery(
                        "SELECT u.id, u.email, u.full_name as \"fullName\", u.image_link as \"imageLink\", u.city, " +
                                "(select sum(CASE WHEN r.count = NULL THEN 0 ELSE r.count END) from reputation r where r.author_id=u.id) as reputation " +
                                "from user_entity u " +
                                "inner join answer a on u.id = a.user_id " +
                                "left join (select voa.answer_id, sum(case when voa.vote = 'UP_VOTE' then 1 else -1 end) as sumv " +
                                "from votes_on_answers voa group by voa.answer_id) as vs on a.id = vs.answer_id " +
                                "where a.persist_date > :date and a.is_deleted = false and u.is_deleted = false " +
                                "group by u.id " +
                                "order by count(a.user_id) desc, sum(sumv) desc, u.id"
                )
                .setParameter("date", LocalDateTime.now().minusDays(days))
                .unwrap(org.hibernate.query.NativeQuery.class)
                .setResultTransformer(new ResultTransformer() {
                    @Override
                    public UserDto transformTuple(Object[] objects, String[] strings) {
                        return new UserDto(
                                ((BigInteger)objects[0]).longValue(),
                                (String)objects[1],
                                (String)objects[2],
                                (String)objects[3],
                                (String)objects[4],
                                ((BigInteger)objects[5]).longValue());
                    }

                    @Override
                    public List transformList(List list) {
                        return list;
                    }
                })
                .setMaxResults(10)
                .getResultList();
    }
}
