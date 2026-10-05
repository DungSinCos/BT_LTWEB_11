package com.mssv24133010.bookstore.service;

import com.mssv24133010.bookstore.dao.AuthorDAO_24133010;
import com.mssv24133010.bookstore.dao.AuthorDAOImpl_24133010;
import com.mssv24133010.bookstore.entity.Author_24133010;
import com.mssv24133010.bookstore.util.PageResult_24133010;
import java.util.List;

public class AuthorService_24133010 {

    private final AuthorDAO_24133010 authorDAO = new AuthorDAOImpl_24133010();

    public PageResult_24133010<Author_24133010> getAuthorsPage(int page, int pageSize) {
        if (page < 1) page = 1;
        int offset = (page - 1) * pageSize;
        List<Author_24133010> items = authorDAO.findPage(offset, pageSize);
        long total = authorDAO.countAll();
        return new PageResult_24133010<>(items, page, pageSize, total);
    }

    public List<Author_24133010> getAllAuthors() {
        return authorDAO.findAll();
    }

    public Author_24133010 getAuthorById(int id) {
        return authorDAO.findById(id);
    }

    public void createAuthor(Author_24133010 author) {
        authorDAO.save(author);
    }

    public void updateAuthor(Author_24133010 author) {
        authorDAO.update(author);
    }

    public void deleteAuthor(int id) {
        authorDAO.delete(id);
    }
}
