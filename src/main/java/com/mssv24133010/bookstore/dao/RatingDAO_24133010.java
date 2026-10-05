package com.mssv24133010.bookstore.dao;

import com.mssv24133010.bookstore.entity.Rating_24133010;
import java.util.List;

public interface RatingDAO_24133010 {
    List<Rating_24133010> findByBookId(int bookId);
    long countByBookId(int bookId);
    Double avgRatingByBookId(int bookId);
    void save(Rating_24133010 rating);
}
