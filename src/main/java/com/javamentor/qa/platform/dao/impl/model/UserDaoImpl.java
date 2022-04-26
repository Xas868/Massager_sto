package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.UserDao;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class UserDaoImpl extends ReadWriteDaoImpl<User, Long> implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Cacheable(value = "userWithRoleByEmail", key = "#email")
    public Optional<User> getWithRoleByEmail(String email) {
        String hql = "select u from User u join fetch u.role r where u.email = :email";
        TypedQuery<User> query = (TypedQuery<User>) entityManager.createQuery(hql).setParameter("email", email);
        return SingleResultUtil.getSingleResultOrNull(query);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "userWithRoleByEmail", key = "#user.email"),
            @CacheEvict(value = "userExistByEmail", key = "#user.email")
    })
    public void update(User user) {
        super.update(user);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "userWithRoleByEmail", key = "#email"),
            @CacheEvict(value = "userExistByEmail", key = "#email")
    })
    public void deleteByName(String email) {
        entityManager
                .createQuery("update User u set u.isDeleted=true where u.email=:email")
                .setParameter("email", email)
                .executeUpdate();
    }

    @Override
    public void deleteById(Long id) {
        entityManager
                .createQuery("update User u set u.isDeleted=true where u.id=:id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "userExistByEmail", key = "#user.email"),
            @CacheEvict(value = "userWithRoleByEmail", key = "#user.email")
    })
    public void delete(User user) {super.delete(user);}

    @Override
    @Caching(evict = {
            @CacheEvict(value = "userWithRoleByEmail", key = "#username"),
            @CacheEvict(value = "userExistByEmail", key = "#username")
    })
    public void changePassword(String password, String username) {
        entityManager
                .createQuery("update User u set u.password = :password where u.email = :username")
                .setParameter("password", password)
                .setParameter("username", username)
                .executeUpdate();
    }

    @Override
    @Cacheable(value = "userExistByEmail", key = "#email")
    public boolean isUserExistByEmail(String email) {
        String hql = "select u from User u where u.email = :email";
        TypedQuery<User> query = (TypedQuery<User>) entityManager.createQuery(hql).setParameter("email", email);
        return SingleResultUtil.getSingleResultOrNull(query).isPresent();
    }
}
