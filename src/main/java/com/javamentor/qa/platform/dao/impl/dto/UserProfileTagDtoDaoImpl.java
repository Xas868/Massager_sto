package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.UserProfileTagDtoDao;
import com.javamentor.qa.platform.models.dto.UserProfileTagDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class UserProfileTagDtoDaoImpl implements UserProfileTagDtoDao {
    private final EntityManager entityManager;

    public UserProfileTagDtoDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<List<UserProfileTagDto>> getAllUserProfileTagDtoByUserId(Long id) {
        return Optional.ofNullable(
                entityManager.createQuery("select new com.javamentor.qa.platform.models.dto.UserProfileTagDto(" +
                                "tt.trackedTag.id," +
                                "tt.trackedTag.name," +
                                "(select coalesce(sum(case when vq.vote = 'UP_VOTE' then 1 else -1 end), 0) from VoteQuestion vq where vq.question.id in elements(tt.trackedTag.questions)) + (select coalesce(sum(case when va.vote = 'UP_VOTE' then 1 else -1 end), 0) from VoteAnswer va where va.answer.question.id in elements(tt.trackedTag.questions))," +
                                "(select coalesce(count(q), 0) from Question q where q.id in elements(tt.trackedTag.questions)) + (select coalesce(count(a), 0) from Answer a where a.question.id in elements(tt.trackedTag.questions))" +
                                ")" +
                                "from TrackedTag tt where tt.user.id = :id", UserProfileTagDto.class)
                        .setParameter("id", id)
                        .getResultList()
        );
    }
}
