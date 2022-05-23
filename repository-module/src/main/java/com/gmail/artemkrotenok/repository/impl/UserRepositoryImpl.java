package com.gmail.artemkrotenok.repository.impl;

import com.gmail.artemkrotenok.repository.UserRepository;
import com.gmail.artemkrotenok.repository.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class UserRepositoryImpl extends GenericRepositoryImpl<Long, User> implements UserRepository {

    private final Logger log = LoggerFactory.getLogger(UserRepositoryImpl.class);

    @Override
    public User getUserByEmail(String email) {
        String hql = "FROM " + entityClass.getSimpleName() + " u WHERE u.email=:email";
        Query query = entityManager.createQuery(hql);
        query.setParameter("email", email);
        return (User) query.getSingleResult();
    }

//    @Override
////    public void addUser(User user) {
////        String sql = "INSERT INTO user(id, surname, name, middle_name, email, password, role, is_deleted) VALUES (null,'" + user.getSurname() + "','" + user.getName() + "','"+ user.getMiddleName() +"','" + user.getEmail() + "','" + user.getPassword() + "','" + user.getRole() + "'," + user.getDeleted() + ");";
////        log.info(sql);
////        Query query = entityManager.createNativeQuery(sql);
////        query.executeUpdate();
////    }

    @Override
    public void addUser(User user) {
        String sql = "INSERT INTO user(id, surname, name, middle_name, email, password, role, is_deleted) VALUES (null," + "?" + "," + "?" + "," + "?" +  ",'" + user.getEmail()+ "'" + ",'" + user.getPassword() + "','" + user.getRole() + "'," + user.getDeleted() + ");";
        log.info(sql);
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, user.getSurname());
        query.setParameter(2, user.getName());
        query.setParameter(3, user.getMiddleName());
        //query.setParameter(4, user.getEmail());
        query.executeUpdate();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getItemsByPageSorted(int startPosition, int itemsByPage) {
        String hql = "FROM " + entityClass.getName() + " u ORDER BY u.email ASC";
        Query query = entityManager.createQuery(hql);
        query.setFirstResult(startPosition);
        query.setMaxResults(itemsByPage);
        return query.getResultList();
    }
}
