package com.mssv24133010.bookstore.service;

import com.mssv24133010.bookstore.dao.RatingDAO_24133010;
import com.mssv24133010.bookstore.dao.RatingDAOImpl_24133010;
import com.mssv24133010.bookstore.entity.Book_24133010;
import com.mssv24133010.bookstore.entity.Rating_24133010;
import com.mssv24133010.bookstore.entity.User_24133010;
import java.util.List;

public class RatingService_24133010 {

    private final RatingDAO_24133010 ratingDAO = new RatingDAOImpl_24133010();

    public List<Rating_24133010> getReviewsForBook(int bookId) {
        return ratingDAO.findByBookId(bookId);
    }

    public long countReviews(int bookId) {
        return ratingDAO.countByBookId(bookId);
    }

    public double averageRating(int bookId) {
        return ratingDAO.avgRatingByBookId(bookId);
    }

    public void submitReview(User_24133010 user, Book_24133010 book, int ratingValue, String reviewText) {
        Rating_24133010 r = new Rating_24133010();
        r.setUser(user);
        r.setBook(book);
        r.setRating(ratingValue);
        r.setReviewText(reviewText);
        ratingDAO.save(r);
    }
}
