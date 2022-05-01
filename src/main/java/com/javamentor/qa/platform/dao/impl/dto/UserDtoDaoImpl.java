package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.UserDtoDao;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.dto.UserDto;
import com.javamentor.qa.platform.models.dto.UserProfileQuestionDto;
import com.javamentor.qa.platform.models.entity.user.reputation.ReputationType;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;
import org.hibernate.type.LongType;
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

//    @Override
//    public List<UserDto> getTop10UsersForWeekRankedByNumberOfQuestions() {
//        return entityManager.createQuery("SELECT NEW com.javamentor.qa.platform.models.dto.UserDto(" +
//                        "u.id, " +
//                        "u.email, " +
//                        "u.fullName, " +
//                        "u.imageLink, " +
//                        "u.city, " +
//                        "(select sum(r.count) from Reputation r where r.author.id=u.id)) " +
//                        "from User u inner join Answer a on u.id = a.user.id " +
//                        "group by u.id " +
//                        "order by count(a.user.id) desc",
//                UserDto.class)
//                .setMaxResults(10)
//                .getResultList();
//    }
    //  "FROM User u inner join (SELECT a.userId FROM Answer a GROUP BY a.userId order by COUNT(a.userId) DESC, (select sum(r.count) from Reputation r where r.author.id=a.userId) DESC, a.userId LIMIT 10) as top on u.id = top.userId and u.isDeleted=false")

//Попытка HQL
//    @Override
//    public List<UserDto> getTop10UsersForWeekRankedByNumberOfQuestions() {
//        String hql = "select u.id, u.email, u.fullName, u.imageLink, u.city, " +
//                "(select sum(r.count) from Reputation r where r.author.id=u.id) as reputation " +
//                "from User u " +
//                "inner join Answer a on u.id = a.user.id " +
//                "left join (select voa.answer.id, sum(case when voa.vote == 'UP_VOTE' then 1 else -1 end) as sumv " +
//                "from VoteAnswer voa group by voa.answer.id) as vs on a.id = vs.answer.id " +
//                "where u.isDeleted = false " +
//                "group by u.id " +
//                "order by count(a.user_id) desc, sum(sumv) desc";
//        return entityManager.createQuery(hql)
//                .setParameter("voteAns", ReputationType.VoteAnswer)
//                .setParameter("voteQuest", ReputationType.VoteQuestion)
//                .setFirstResult(offset)
//                .setMaxResults(itemsOnPage)
//                .unwrap(org.hibernate.query.Query.class)
//                .setResultTransformer(new ResultTransformer() {
//                    @Override
//                    public UserDto transformTuple(Object[] objects, String[] strings) {
//                        return new UserDto(
//                                (Long)objects[0],
//                                (String)objects[1],
//                                (String)objects[2],
//                                (String)objects[3],
//                                (String)objects[4],
//                                (Long)objects[5]);
//                    }
//
//                    @Override
//                    public List transformList(List list) {
//                        return list;
//                    }
//                })
//                .getResultList();
//    }


//    РАБОТАЕТ NATIVESQL
    @Override
    public List<UserDto> getTop10UsersForWeekRankedByNumberOfQuestions() {
        return entityManager.createNativeQuery(
                        "SELECT u.id, u.email, u.full_name as \"fullName\", u.image_link as \"imageLink\", u.city, " +
                                "(select sum(CASE WHEN r.count = NULL THEN 0 ELSE r.count END) from reputation r where r.author_id=u.id) as reputation " +
                                "from user_entity u " +
                                "inner join answer a on u.id = a.user_id " +
                                "left join (select voa.answer_id, sum(case when voa.vote = 'UP_VOTE' then 1 else -1 end) as sumv " +
                                "from votes_on_answers voa group by voa.answer_id) as vs on a.id = vs.answer_id " +
                                "where a.persist_date > :date and u.is_deleted = false " +
                                "group by u.id " +
                                "order by count(a.user_id) desc, sum(sumv) desc, u.id"
                )
                .setParameter("date", LocalDateTime.now().minusWeeks(1))
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
                .getResultList();
    }

//    @Override
//    public List<UserDto> getTop10UsersForWeekRankedByNumberOfQuestions() {
//        return entityManager.createQuery(
//                        "SELECT u.id as id, u.email as email, u.fullName as fullName, u.imageLink as imageLink, u.city as city, " +
//                                "(select sum(r.count) from Reputation r where r.author.id=u.id) as reputation " +
//                                "from User u " +
//                                "inner join Answer a on u.id = a.user.id " +
//                                "left join VoteAnswer va on a.id = va.answer.id " +
//                                "where a.persistDateTime > :date and u.isDeleted=false " +
//                                "group by u.id, a.user.id " +
//                                "order by count(a.user.id) desc"
//                )
//                .setParameter("date", LocalDateTime.now().minusWeeks(2))
//                .unwrap(org.hibernate.query.Query.class)
//                .setResultTransformer(Transformers.aliasToBean(UserDto.class))
//                .setMaxResults(20)
//                .getResultList();
//    }


//    "left join VoteAnswer va on a.id = va.answer.id " +
    //"where a.persistDateTime > :date and u.isDeleted=false " +

    // , sum(case when va.vote = 'UP_VOTE' then 1 else -1 end) desc
    //  "FROM User u inner join (SELECT a.userId FROM Answer a GROUP BY a.userId order by COUNT(a.userId) DESC, (select sum(r.count) from Reputation r where r.author.id=a.userId) DESC, a.userId LIMIT 10) as top on u.id = top.userId and u.isDeleted=false")

    //ЭТО РАБОТАЕТ
//    @Override
//    public List<UserDto> getTop10UsersForWeekRankedByNumberOfQuestions() {
//        return entityManager.createQuery(
//                        "SELECT u.id as id, u.email as email, u.fullName as fullName, u.imageLink as imageLink, u.city as city, " +
//                                "(select sum(r.count) from Reputation r where r.author.id=u.id) as reputation " +
//                                "from User u " +
//                                "inner join Answer a on u.id = a.user.id " +
//                                "where a.persistDateTime > :date and u.isDeleted=false " +
//                                "group by u.id, a.user.id " +
//                                "order by count(a.user.id) desc"
//                )
//                .setParameter("date", LocalDateTime.now().minusWeeks(2))
//                .unwrap(org.hibernate.query.Query.class)
//                .setResultTransformer(Transformers.aliasToBean(UserDto.class))
//                .setMaxResults(20)
//                .getResultList();
//    }
}
