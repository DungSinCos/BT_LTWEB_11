package com.mssv24133010.bookstore.dao;

import com.mssv24133010.bookstore.entity.Author_24133010;
import com.mssv24133010.bookstore.entity.Book_24133010;
import com.mssv24133010.bookstore.entity.BookAuthor_24133010;
import com.mssv24133010.bookstore.util.JPAUtil_24133010;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class BookAuthorDAO_24133010 {

    public List<Author_24133010> findAuthorsByBookId(int bookId) {
        EntityManager em = JPAUtil_24133010.getEntityManager();
        try {
            TypedQuery<Author_24133010> q = em.createQuery(
                    "SELECT ba.author FROM BookAuthor_24133010 ba WHERE ba.book.bookId = :bookId", Author_24133010.class);
            q.setParameter("bookId", bookId);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    /** Replace all author links of a book with the given author id list. */
    public void setAuthorsForBook(int bookId, List<Integer> authorIds) {
        EntityManager em = JPAUtil_24133010.getEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM BookAuthor_24133010 ba WHERE ba.book.bookId = :bookId")
                    .setParameter("bookId", bookId)
                    .executeUpdate();
            Book_24133010 book = em.find(Book_24133010.class, bookId);
            if (authorIds != null) {
                for (Integer authorId : authorIds) {
                    Author_24133010 author = em.find(Author_24133010.class, authorId);
                    if (author != null) {
                        em.persist(new BookAuthor_24133010(book, author));
                    }
                }
            }
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
