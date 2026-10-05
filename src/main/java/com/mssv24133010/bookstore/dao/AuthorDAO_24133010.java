package com.mssv24133010.bookstore.dao;

import com.mssv24133010.bookstore.entity.Author_24133010;
import java.util.List;

public interface AuthorDAO_24133010 {
    List<Author_24133010> findPage(int offset, int limit);
    long countAll();
    List<Author_24133010> findAll();
    Author_24133010 findById(int authorId);
    void save(Author_24133010 author);
    void update(Author_24133010 author);
    void delete(int authorId);
}
