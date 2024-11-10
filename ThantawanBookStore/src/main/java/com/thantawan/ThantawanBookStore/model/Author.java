package com.thantawan.ThantawanBookStore.model;

import jakarta.persistence.*;
import org.springframework.data.relational.core.mapping.Table;

@Entity
@Table(name = "Author")
public class Author {

    @Id
    @Column(name = "LessorID", nullable = false)
    private String lessorId;

    @Id
    @Column(name = "BookID", nullable = false)
    private String bookId;

    @Id
    @Column(name = "Author", nullable = false)
    private String author;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "LessorID", referencedColumnName = "LessorID", insertable = false, updatable = false),
            @JoinColumn(name = "BookID", referencedColumnName = "BookID", insertable = false, updatable = false)
    })
    private Book book;

    // getters and setters
    public String getLessorId() {
        return lessorId;
    }

    public void setLessorId(String lessorId) {
        this.lessorId = lessorId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

}