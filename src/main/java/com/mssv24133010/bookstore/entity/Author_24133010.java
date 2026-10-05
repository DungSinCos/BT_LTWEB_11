package com.mssv24133010.bookstore.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@Table(name = "author")
public class Author_24133010 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    private Integer authorId;

    @Column(name = "author_name", length = 100, columnDefinition = "nvarchar(100)")
    private String authorName;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookAuthor_24133010> bookAuthors;

    public Author_24133010() {}

    public Integer getAuthorId() { return authorId; }
    public void setAuthorId(Integer authorId) { this.authorId = authorId; }
    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getFormattedDateOfBirth() {
        return dateOfBirth != null ? dateOfBirth.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "";
    }

    public List<BookAuthor_24133010> getBookAuthors() { return bookAuthors; }
    public void setBookAuthors(List<BookAuthor_24133010> bookAuthors) { this.bookAuthors = bookAuthors; }
}
