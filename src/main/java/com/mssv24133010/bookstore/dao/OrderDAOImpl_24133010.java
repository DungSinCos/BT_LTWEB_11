package com.mssv24133010.bookstore.dao;

import com.mssv24133010.bookstore.entity.Order_24133010;
import com.mssv24133010.bookstore.util.JPAUtil_24133010;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class OrderDAOImpl_24133010 implements OrderDAO_24133010 {

    @Override
    public void save(Order_24133010 order) {
        EntityManager em = JPAUtil_24133010.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(order);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public Order_24133010 findById(int orderId) {
        EntityManager em = JPAUtil_24133010.getEntityManager();
        try {
            // Dùng JPQL để eager-load orderItems và book trong 1 query tránh LazyInit
            TypedQuery<Order_24133010> q = em.createQuery(
                "SELECT DISTINCT o FROM Order_24133010 o " +
                "LEFT JOIN FETCH o.orderItems oi " +
                "LEFT JOIN FETCH oi.book " +
                "WHERE o.orderId = :id", Order_24133010.class);
            q.setParameter("id", orderId);
            List<Order_24133010> result = q.getResultList();
            return result.isEmpty() ? null : result.get(0);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Order_24133010> findByUser(int userId) {
        EntityManager em = JPAUtil_24133010.getEntityManager();
        try {
            TypedQuery<Order_24133010> q = em.createQuery(
                "SELECT DISTINCT o FROM Order_24133010 o " +
                "LEFT JOIN FETCH o.orderItems oi " +
                "LEFT JOIN FETCH oi.book " +
                "WHERE o.user.id = :uid " +
                "ORDER BY o.createdAt DESC", Order_24133010.class);
            q.setParameter("uid", userId);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Order_24133010> findByUserAndStatus(int userId, Order_24133010.Status status) {
        EntityManager em = JPAUtil_24133010.getEntityManager();
        try {
            String jpql;
            TypedQuery<Order_24133010> q;
            if (status == null) {
                jpql = "SELECT DISTINCT o FROM Order_24133010 o " +
                       "LEFT JOIN FETCH o.orderItems oi " +
                       "LEFT JOIN FETCH oi.book " +
                       "WHERE o.user.id = :uid " +
                       "ORDER BY o.createdAt DESC";
                q = em.createQuery(jpql, Order_24133010.class);
                q.setParameter("uid", userId);
            } else {
                jpql = "SELECT DISTINCT o FROM Order_24133010 o " +
                       "LEFT JOIN FETCH o.orderItems oi " +
                       "LEFT JOIN FETCH oi.book " +
                       "WHERE o.user.id = :uid AND o.status = :status " +
                       "ORDER BY o.createdAt DESC";
                q = em.createQuery(jpql, Order_24133010.class);
                q.setParameter("uid", userId);
                q.setParameter("status", status);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Order_24133010 order) {
        EntityManager em = JPAUtil_24133010.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(order);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
