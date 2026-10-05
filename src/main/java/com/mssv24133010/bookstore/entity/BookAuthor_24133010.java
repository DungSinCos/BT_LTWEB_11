package com.mssv24133010.bookstore.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "book_author")
@IdClass(BookAuthorId_24133010.class)
public class BookAuthor_24133010 {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookid")
    private Book_24133010 book;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author_24133010 author;

    public BookAuthor_24133010() {}

    public BookAuthor_24133010(Book_24133010 book, Author_24133010 author) {
        this.book = book;
        this.author = author;
    }

    public Book_24133010 getBook() { return book; }
    public void setBook(Book_24133010 book) { this.book = book; }
    public Author_24133010 getAuthor() { return author; }
    public void setAuthor(Author_24133010 author) { this.author = author; }
}
