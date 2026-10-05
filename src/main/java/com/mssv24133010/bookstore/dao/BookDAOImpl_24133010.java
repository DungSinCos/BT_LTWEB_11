package com.mssv24133010.bookstore.dao;

import com.mssv24133010.bookstore.entity.Book_24133010;
import com.mssv24133010.bookstore.util.JPAUtil_24133010;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class BookDAOImpl_24133010 implements BookDAO_24133010 {

    @Override
    public List<Book_24133010> findPage(int offset, int limit) {
        EntityManager em = JPAUtil_24133010.getEntityManager();
        try {
            TypedQuery<Book_24133010> q = em.createQuery(
                    "SELECT b FROM Book_24133010 b ORDER BY b.bookId DESC", Book_24133010.class);
            q.setFirstResult(offset);
            q.setMaxResults(limit);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public long countAll() {
        EntityManager em = JPAUtil_24133010.getEntityManager();
        try {
            return em.createQuery("SELECT COUNT(b) FROM Book_24133010 b", Long.class).getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public Book_24133010 findById(int bookId) {
        EntityManager em = JPAUtil_24133010.getEntityManager();
        try {
            return em.find(Book_24133010.class, bookId);
        } finally {
            em.close();
        }
    }

    @Override
    public void save(Book_24133010 book) {
        EntityManager em = JPAUtil_24133010.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(book);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Book_24133010 book) {
        EntityManager em = JPAUtil_24133010.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(book);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(int bookId) {
        EntityManager em = JPAUtil_24133010.getEntityManager();
        try {
            em.getTransaction().begin();
            Book_24133010 b = em.find(Book_24133010.class, bookId);
            if (b != null) em.remove(b);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
