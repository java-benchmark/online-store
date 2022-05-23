package com.gmail.artemkrotenok.repository.impl;

import java.util.List;

import javax.persistence.Query;

import com.gmail.artemkrotenok.repository.OrderRepository;
import com.gmail.artemkrotenok.repository.model.Order;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepositoryImpl extends GenericRepositoryImpl<Long, Order> implements OrderRepository {

    @Override
    @SuppressWarnings("unchecked")
    public List<Order> getItemsByPageSorted(int startPosition, int itemsByPage) {
        String hql = " FROM " + entityClass.getName() + " i ORDER BY i.date DESC";
        Query query = entityManager.createQuery(hql);
        query.setFirstResult(startPosition);
        query.setMaxResults(itemsByPage);
        return query.getResultList();
    }

    @Override
    public Long getCountByUser(String userEmail) {
        String hql = "SELECT COUNT(*) FROM " + entityClass.getSimpleName() + " i" +
                " WHERE i.user.email=:userEmail";
        Query query = entityManager.createQuery(hql);
        query.setParameter("userEmail", userEmail);
        return (Long) query.getSingleResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Order> getItemsByPageSortedByUser(int startPosition, int itemsByPage, String userEmail) {
        String hql = "FROM " + entityClass.getName() + " i" +
                " WHERE i.user.email=:userEmail" +
                " ORDER BY i.date DESC";
        Query query = entityManager.createQuery(hql);
        query.setParameter("userEmail", userEmail);
        query.setFirstResult(startPosition);
        query.setMaxResults(itemsByPage);
        return query.getResultList();
    }

}
