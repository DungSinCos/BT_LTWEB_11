package com.mssv24133010.bookstore.dao;

import com.mssv24133010.bookstore.entity.Rating_24133010;
import com.mssv24133010.bookstore.util.JPAUtil_24133010;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class RatingDAOImpl_24133010 implements RatingDAO_24133010 {

    @Override
    public List<Rating_24133010> findByBookId(int bookId) {
        EntityManager em = JPAUtil_24133010.getEntityManager();
        try {
            TypedQuery<Rating_24133010> q = em.createQuery(
                    "SELECT r FROM Rating_24133010 r WHERE r.book.bookId = :bookId", Rating_24133010.class);
            q.setParameter("bookId", bookId);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public long countByBookId(int bookId) {
        EntityManager em = JPAUtil_24133010.getEntityManager();
        try {
            TypedQuery<Long> q = em.createQuery(
                    "SELECT COUNT(r) FROM Rating_24133010 r WHERE r.book.bookId = :bookId", Long.class);
            q.setParameter("bookId", bookId);
            return q.getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public Double avgRatingByBookId(int bookId) {
        EntityManager em = JPAUtil_24133010.getEntityManager();
        try {
            TypedQuery<Double> q = em.createQuery(
                    "SELECT AVG(r.rating) FROM Rating_24133010 r WHERE r.book.bookId = :bookId", Double.class);
            q.setParameter("bookId", bookId);
            Double result = q.getSingleResult();
            return result == null ? 0.0 : result;
        } finally {
            em.close();
        }
    }

    @Override
    public void save(Rating_24133010 rating) {
        EntityManager em = JPAUtil_24133010.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(rating); // merge: cho phep 1 user cap nhat lai review cua chinh minh
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
