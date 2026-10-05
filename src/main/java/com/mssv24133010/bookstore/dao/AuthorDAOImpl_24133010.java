package com.mssv24133010.bookstore.dao;

import com.mssv24133010.bookstore.entity.Author_24133010;
import com.mssv24133010.bookstore.util.JPAUtil_24133010;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class AuthorDAOImpl_24133010 implements AuthorDAO_24133010 {

    @Override
    public List<Author_24133010> findPage(int offset, int limit) {
        EntityManager em = JPAUtil_24133010.getEntityManager();
        try {
            TypedQuery<Author_24133010> q = em.createQuery(
                    "SELECT a FROM Author_24133010 a ORDER BY a.authorId DESC", Author_24133010.class);
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
            return em.createQuery("SELECT COUNT(a) FROM Author_24133010 a", Long.class).getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Author_24133010> findAll() {
        EntityManager em = JPAUtil_24133010.getEntityManager();
        try {
            return em.createQuery("SELECT a FROM Author_24133010 a ORDER BY a.authorName", Author_24133010.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Author_24133010 findById(int authorId) {
        EntityManager em = JPAUtil_24133010.getEntityManager();
        try {
            return em.find(Author_24133010.class, authorId);
        } finally {
            em.close();
        }
    }

    @Override
    public void save(Author_24133010 author) {
        EntityManager em = JPAUtil_24133010.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(author);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Author_24133010 author) {
        EntityManager em = JPAUtil_24133010.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(author);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(int authorId) {
        EntityManager em = JPAUtil_24133010.getEntityManager();
        try {
            em.getTransaction().begin();
            Author_24133010 a = em.find(Author_24133010.class, authorId);
            if (a != null) em.remove(a);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
