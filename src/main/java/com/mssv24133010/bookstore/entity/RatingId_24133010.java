package com.mssv24133010.bookstore.entity;

import java.io.Serializable;
import java.util.Objects;

public class RatingId_24133010 implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer user;
    private Integer book;

    public RatingId_24133010() {}

    public RatingId_24133010(Integer user, Integer book) {
        this.user = user;
        this.book = book;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RatingId_24133010)) return false;
        RatingId_24133010 that = (RatingId_24133010) o;
        return Objects.equals(user, that.user) && Objects.equals(book, that.book);
    }

    @Override
    public int hashCode() { return Objects.hash(user, book); }
}
