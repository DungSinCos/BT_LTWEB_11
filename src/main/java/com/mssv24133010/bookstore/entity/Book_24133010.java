package com.mssv24133010.bookstore.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@Table(name = "books")
public class Book_24133010 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookid")
    private Integer bookId;

    @Column(name = "isbn", length = 20)
    private String isbn;

    @Column(name = "title", length = 200, columnDefinition = "nvarchar(200)")
    private String title;

    @Column(name = "publisher", length = 100, columnDefinition = "nvarchar(100)")
    private String publisher;

    @Column(name = "price", precision = 6, scale = 2)
    private BigDecimal price;

    @Lob
    @Column(name = "description", columnDefinition = "nvarchar(max)")
    private String description;

    @Column(name = "publish_date")
    private LocalDate publishDate;

    @Column(name = "cover_image", length = 100)
    private String coverImage;

    @Column(name = "quantity")
    private Integer quantity;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookAuthor_24133010> bookAuthors;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rating_24133010> ratings;

    public Book_24133010() {}

    // getters / setters
    public Integer getBookId() { return bookId; }
    public void setBookId(Integer bookId) { this.bookId = bookId; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDate getPublishDate() { return publishDate; }
    public void setPublishDate(LocalDate publishDate) { this.publishDate = publishDate; }

    public String getFormattedPublishDate() {
        return publishDate != null ? publishDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "";
    }

    public String getCoverImage() { return coverImage; }
    public void setCoverImage(String coverImage) { this.coverImage = coverImage; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public List<BookAuthor_24133010> getBookAuthors() { return bookAuthors; }
    public void setBookAuthors(List<BookAuthor_24133010> bookAuthors) { this.bookAuthors = bookAuthors; }
    public List<Rating_24133010> getRatings() { return ratings; }
    public void setRatings(List<Rating_24133010> ratings) { this.ratings = ratings; }
}
