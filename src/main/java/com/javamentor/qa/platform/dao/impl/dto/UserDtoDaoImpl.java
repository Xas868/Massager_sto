package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.UserDtoDao;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.dto.AnswerDTO;
import com.javamentor.qa.platform.models.dto.UserDto;
import com.javamentor.qa.platform.models.dto.UserProfileQuestionDto;
import com.javamentor.qa.platform.models.dto.UserProfileVoteDto;
import com.javamentor.qa.platform.models.entity.question.answer.VoteType;
import com.javamentor.qa.platform.models.util.CalendarPeriod;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class UserDtoDaoImpl implements UserDtoDao {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<UserProfileQuestionDto> getAllUserProfileQuestionDtoById(Long id) {
        return entityManager.createQuery("select new com.javamentor.qa.platform.models.dto.UserProfileQuestionDto(" +
                        "q.id,q.title," +
                        "coalesce((select count(a.id) from Answer a where a.question.id=q.id),0),q.persistDateTime," +
                        "(select count (qv.question.id) from QuestionViewed qv where qv.question.id = q.id)," +
                        "(select coalesce(sum(case when vq.vote = 'UP_VOTE' then 1 else -1 end), 0) from VoteQuestion vq where vq.question.id = q.id))" +
                        "from Question q where q.user.id=:id", UserProfileQuestionDto.class)
                .setParameter("id", id)
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
        return (List<UserProfileQuestionDto>) entityManager.createQuery(
                "select new com.javamentor.qa.platform.models.dto.UserProfileQuestionDto(" +
                        "q.id,q.title," +
                        "coalesce((select count(a.id) from Answer a where a.question.id=q.id),0),q.persistDateTime," +
                        "(select count (qv.question.id) from QuestionViewed qv where qv.question.id = q.id)," +
                        "(select coalesce(sum(case when vq.vote = 'UP_VOTE' then 1 else -1 end), 0) from VoteQuestion vq where vq.question.id = q.id))" +
                        "from Question q where q.user.id=:id and  q.isDeleted=true", UserProfileQuestionDto.class)


                .setParameter("id", id)
                .getResultList();
    }

    @Override
    public List<UserDto> getTopUsersForDaysRankedByNumberOfQuestions(CalendarPeriod calendarPeriod) {
        String hql = "select u.id, u.email, u.fullName, u.imageLink, u.city, " +
                "(select sum(case when r.count is null then 0 else r.count end) from Reputation r where r.author.id=u.id) as reputation, " +
                "sum(case when voa.vote = 'UP_VOTE' then 1 when voa.vote = 'DOWN_VOTE' then -1 else 0 end) as sumVotes, " +
                "count(distinct a.id) as answersCount " +
                "from User u " +
                "inner join Answer a on u.id = a.user.id " +
                "left join VoteAnswer voa on a.id = voa.answer.id " +
                "where a.persistDateTime > :date and a.isDeleted = false and u.isDeleted = false " +
                "group by u.id " +
                "order by answersCount desc, sumVotes desc, u.id";
        return entityManager.createQuery(hql)
                .setParameter("date", LocalDateTime.now().minusDays(calendarPeriod.getDays()))
                .unwrap(org.hibernate.query.Query.class)
                .setResultTransformer(new ResultTransformer() {
                    @Override
                    public UserDto transformTuple(Object[] objects, String[] strings) {
                        return new UserDto(
                                (Long) objects[0],
                                (String) objects[1],
                                (String) objects[2],
                                (String) objects[3],
                                (String) objects[4],
                                (Long) objects[5]);
                    }

                    @Override
                    public List transformList(List list) {
                        return list;
                    }
                })
                .setMaxResults(10)
                .getResultList();
    }

    @Override
    public Long getCountAnswersPerWeekByUserId(Long userId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
        LocalDateTime weekAgo = LocalDateTime.parse((LocalDateTime.now().minusDays(7)).format(formatter), formatter);
        List<AnswerDTO> resultList = entityManager.createQuery("select new com.javamentor.qa.platform.models.dto.AnswerDTO(answ.id, answ.user.id, " +
                        "(select sum(r.count) from Reputation r where r.author.id = answ.user.id), " +
                        "answ.question.id, answ.htmlBody, answ.persistDateTime, answ.isHelpful, answ.dateAcceptTime, " +
                        "(select coalesce(sum(case when v.vote = 'UP_VOTE' then 1 else -1 end), 0) from VoteAnswer v " +
                        "where v.answer.id = answ.id), answ.user.imageLink, answ.user.nickname)" +
                        "from Answer as answ where answ.user.id = :user_id and answ.persistDateTime >= :week_ago", AnswerDTO.class)
                .setParameter("user_id", userId)
                .setParameter("week_ago", weekAgo)
                .getResultList();

        return (long) resultList.size();
    }

    @Override
    public List<Long> getUnregisteredUserIds(List<Long> userIds) {
        List<Long> foundIds = entityManager.createQuery("select id from User where id in(:ids)")
                .setParameter("ids", userIds)
                .getResultList();

        List<Long> notFoundIds = userIds.stream().filter(aObject -> !foundIds.contains(aObject)).collect(Collectors.toList());

        return notFoundIds;
    }

    @Override
    public Optional<UserProfileVoteDto> getCountVotesAnswersAndQuestions(Long id) {
        return SingleResultUtil.getSingleResultOrNull(entityManager
                .createQuery("select new com.javamentor.qa.platform.models.dto.UserProfileVoteDto(" +
                        "(select coalesce(sum(case when voa.vote = :upVote then 1 else 0 end), 0) from VoteAnswer voa where voa.user.id = u.id) " +
                        "+ (select coalesce(sum(case when voq.vote = :upVote then 1 else 0 end), 0) from VoteQuestion voq where voq.user.id = u.id), " +
                        "(select coalesce(sum(case when voan.vote = :downVote then 1 else 0 end), 0) from VoteAnswer voan where voan.user.id = u.id) " +
                        "+ (select coalesce(sum(case when voqu.vote = :downVote then 1 else 0 end), 0) from VoteQuestion voqu where voqu.user.id = u.id), " +
                        "(select count(qv.id) from VoteQuestion qv where qv.user.id = u.id), " +
                        "(select count(an.id) from VoteAnswer an where an.user.id = u.id), " +
                        "(select count(van.id) from VoteAnswer van where van.user.id = u.id and van.persistDateTime >= :date)" +
                        "+ (select count(vqu.id) from VoteQuestion vqu where vqu.user.id = u.id and vqu.localDateTime >= :date)) " +
                        "from User u where u.id = :id",UserProfileVoteDto.class)
                .setParameter("date", LocalDateTime.now().minusDays(30))
                .setParameter("upVote", VoteType.UP_VOTE)
                .setParameter("downVote", VoteType.DOWN_VOTE)
                .setParameter("id", id));


    }

}
