package com.mssv24133010.bookstore.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "rating")
@IdClass(RatingId_24133010.class)
public class Rating_24133010 {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid")
    private User_24133010 user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookid")
    private Book_24133010 book;

    @Column(name = "rating")
    private Integer rating; // tinyint 1-5

    @Lob
    @Column(name = "review_text", columnDefinition = "nvarchar(max)")
    private String reviewText;

    public Rating_24133010() {}

    public User_24133010 getUser() { return user; }
    public void setUser(User_24133010 user) { this.user = user; }
    public Book_24133010 getBook() { return book; }
    public void setBook(Book_24133010 book) { this.book = book; }
    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }
    public String getReviewText() { return reviewText; }
    public void setReviewText(String reviewText) { this.reviewText = reviewText; }
}
