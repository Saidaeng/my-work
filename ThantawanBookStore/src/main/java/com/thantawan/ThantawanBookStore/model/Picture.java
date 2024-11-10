package com.thantawan.ThantawanBookStore.model;

import jakarta.persistence.*;
import org.springframework.data.relational.core.mapping.Table;

@Entity
@Table(name = "Picture")
public class Picture {

    @Id
    @Column(name = "LessorID", nullable = false)
    private String lessorId;

    @Id
    @Column(name = "BookID", nullable = false)
    private String bookId;

    @Id
    @Column(name = "picture_hash", nullable = false)
    private String pictureHash;

    @Lob
    @Column(name = "picture", nullable = false, columnDefinition="MEDIUMBLOB")
    private byte[] picture;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "LessorID", referencedColumnName = "LessorID", insertable = false, updatable = false),
            @JoinColumn(name = "BookID", referencedColumnName = "BookID", insertable = false, updatable = false)
    })
    private Book book;

    public String getPictureHash(){return pictureHash;};
    public void setPictureHash(String pictureHash) { this.pictureHash=pictureHash;};

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

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

    public Book getBook() {
        return book;
    }

    public void setBook() {
        this.book = book;
    }

}
