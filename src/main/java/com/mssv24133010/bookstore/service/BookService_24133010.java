package com.mssv24133010.bookstore.service;

import com.mssv24133010.bookstore.dao.BookAuthorDAO_24133010;
import com.mssv24133010.bookstore.dao.BookDAO_24133010;
import com.mssv24133010.bookstore.dao.BookDAOImpl_24133010;
import com.mssv24133010.bookstore.entity.Author_24133010;
import com.mssv24133010.bookstore.entity.Book_24133010;
import com.mssv24133010.bookstore.util.PageResult_24133010;
import java.util.List;

public class BookService_24133010 {

    private final BookDAO_24133010 bookDAO = new BookDAOImpl_24133010();
    private final BookAuthorDAO_24133010 bookAuthorDAO = new BookAuthorDAO_24133010();
    public static final int PAGE_SIZE = 6; // 6 san pham / trang theo yeu cau Cau 3

    public PageResult_24133010<Book_24133010> getBooksPage(int page) {
        if (page < 1) page = 1;
        int offset = (page - 1) * PAGE_SIZE;
        List<Book_24133010> items = bookDAO.findPage(offset, PAGE_SIZE);
        long total = bookDAO.countAll();
        return new PageResult_24133010<>(items, page, PAGE_SIZE, total);
    }

    public PageResult_24133010<Book_24133010> getBooksPageForAdmin(int page, int pageSize) {
        if (page < 1) page = 1;
        int offset = (page - 1) * pageSize;
        List<Book_24133010> items = bookDAO.findPage(offset, pageSize);
        long total = bookDAO.countAll();
        return new PageResult_24133010<>(items, page, pageSize, total);
    }

    public Book_24133010 getBookById(int id) {
        return bookDAO.findById(id);
    }

    public List<Author_24133010> getAuthorsOfBook(int bookId) {
        return bookAuthorDAO.findAuthorsByBookId(bookId);
    }

    public void createBook(Book_24133010 book, List<Integer> authorIds) {
        bookDAO.save(book);
        bookAuthorDAO.setAuthorsForBook(book.getBookId(), authorIds);
    }

    public void updateBook(Book_24133010 book, List<Integer> authorIds) {
        bookDAO.update(book);
        bookAuthorDAO.setAuthorsForBook(book.getBookId(), authorIds);
    }

    public void deleteBook(int bookId) {
        bookDAO.delete(bookId);
    }
}
