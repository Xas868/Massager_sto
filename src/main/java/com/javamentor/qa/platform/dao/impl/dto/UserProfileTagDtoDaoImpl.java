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
                                "(select coalesce((select coalesce(sum(case when vq.vote = 'UP_VOTE' then 1 else -1 end), 0) from vq where vq.question.id = tt.trackedTag.id) , 0) + 5 from vq, va, tt) " +
                                ")" +
                                "from TrackedTag tt, VoteAnswer va, VoteQuestion vq where tt.user.id = :id", UserProfileTagDto.class)
                        .setParameter("id", id)
                        .getResultList()
        );
    }
}
