package com.mssv24133010.bookstore.dao;

import com.mssv24133010.bookstore.entity.User_24133010;
import com.mssv24133010.bookstore.util.JPAUtil_24133010;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class UserDAOImpl_24133010 implements UserDAO_24133010 {

    @Override
    public User_24133010 findByEmail(String email) {
        EntityManager em = JPAUtil_24133010.getEntityManager();
        try {
            TypedQuery<User_24133010> q = em.createQuery(
                    "SELECT u FROM User_24133010 u WHERE u.email = :email", User_24133010.class);
            q.setParameter("email", email);
            return q.getResultList().stream().findFirst().orElse(null);
        } finally {
            em.close();
        }
    }

    @Override
    public User_24133010 findById(int id) {
        EntityManager em = JPAUtil_24133010.getEntityManager();
        try {
            return em.find(User_24133010.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public void save(User_24133010 user) {
        EntityManager em = JPAUtil_24133010.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void update(User_24133010 user) {
        EntityManager em = JPAUtil_24133010.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean existsByEmail(String email) {
        return findByEmail(email) != null;
    }
}
