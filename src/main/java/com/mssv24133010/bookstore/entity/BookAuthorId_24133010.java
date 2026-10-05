package com.mssv24133010.bookstore.entity;

import java.io.Serializable;
import java.util.Objects;

public class BookAuthorId_24133010 implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer book;
    private Integer author;

    public BookAuthorId_24133010() {}

    public BookAuthorId_24133010(Integer book, Integer author) {
        this.book = book;
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookAuthorId_24133010)) return false;
        BookAuthorId_24133010 that = (BookAuthorId_24133010) o;
        return Objects.equals(book, that.book) && Objects.equals(author, that.author);
    }

    @Override
    public int hashCode() { return Objects.hash(book, author); }
}
