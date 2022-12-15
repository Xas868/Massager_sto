package com.javamentor.qa.platform.dao.impl.pagination.user_profile_dto;

import com.javamentor.qa.platform.dao.abstracts.dto.UserProfileDtoDao;
import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;
import com.javamentor.qa.platform.models.dto.UserProfileAnswerDto;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import com.javamentor.qa.platform.models.entity.question.answer.ProfileAnswerSort;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.UserProfileDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository("UserProfileDtoDaoImpl")
public class UserProfileDtoDaoImpl implements PageDtoDao<UserProfileAnswerDto>, UserProfileDtoDao {
    @PersistenceContext
    private EntityManager entityManager;
    private List<UserProfileAnswerDto> userProfileAnswerDtos = new ArrayList<>();
    private ProfileAnswerSort profileAnswerSort;
    private int itemsOnPage;
    private int offset;

    @Override
    public List<UserProfileAnswerDto> getPaginationItems(PaginationData properties) {
        itemsOnPage = properties.getItemsOnPage();
        offset = (properties.getCurrentPage() - 1) * itemsOnPage;
        User user = (User) properties.getProps().get("user");
        ProfileAnswerSort profileAnswerSort = (ProfileAnswerSort) properties.getProps().get("profileAnswerSort");
        if (this.profileAnswerSort != null && !profileAnswerSort.equals(this.profileAnswerSort)){
            userProfileAnswerDtos.sort(profileAnswerSort.getComparator());
        }

        this.profileAnswerSort = profileAnswerSort;

        userProfileAnswerDtos.addAll(
                getAllUserProfileAnswerDtoById(user.getId()).stream()
                        .sorted(profileAnswerSort.getComparator())
                        .collect(Collectors.toList())
        );

        return userProfileAnswerDtos.subList(offset, Math.min((offset + itemsOnPage), userProfileAnswerDtos.size()));
    }

    @Override
    public Long getTotalResultCount(Map<String, Object> properties) {
        return (long) userProfileAnswerDtos.size();
    }
    @Override
    public String toString() {
        return getClass().getName();
    }

    @Override
    public List<UserProfileAnswerDto> getAllUserProfileAnswerDtoById(Long id) {
        List<UserProfileAnswerDto> id1 = entityManager.createQuery("select new com.javamentor.qa.platform.models.dto.UserProfileAnswerDto(" +
                        "a.id," +
                        "a.question.title," +
                        "(select count (av.answer.id) from AnswerViewed av where av.answer.id = a.id)," +
                        "(select coalesce(sum(case when va.vote = 'UP_VOTE' then 1 else -1 end), 0) from VoteAnswer va where va.answer.id = a.id)," +
                        "a.question.id, a.persistDateTime)" +
                        "from Answer a where a.user.id=:id", UserProfileAnswerDto.class)
                .setFirstResult(userProfileAnswerDtos.size())
                .setParameter("id", id)
                .getResultList();
        return id1;
    }
}
