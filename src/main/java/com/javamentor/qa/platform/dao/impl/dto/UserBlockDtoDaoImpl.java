package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.UserBlockDtoDao;
import com.javamentor.qa.platform.models.dto.UserBlockDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserBlockDtoDaoImpl implements UserBlockDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<UserBlockDto> getAllBlockedUsers(Long id) {
        return entityManager.createQuery("select new com.javamentor.qa.platform.models.dto.UserBlockDto(" +
                        "bch.id, bch.profile.fullName, bch.persistDate) " +
                        "from BlockChatUserList bch where bch.blocked.id" +
                        " = :id order by bch.persistDate", UserBlockDto.class)
                .setParameter("id", id)
                .getResultList();
    }

}
