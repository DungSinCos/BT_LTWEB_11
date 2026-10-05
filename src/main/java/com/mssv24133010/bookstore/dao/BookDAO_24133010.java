package com.mssv24133010.bookstore.dao;

import com.mssv24133010.bookstore.entity.Book_24133010;
import java.util.List;

public interface BookDAO_24133010 {
    List<Book_24133010> findPage(int offset, int limit);
    long countAll();
    Book_24133010 findById(int bookId);
    void save(Book_24133010 book);
    void update(Book_24133010 book);
    void delete(int bookId);
}
