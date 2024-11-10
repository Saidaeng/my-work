package com.thantawan.ThantawanBookStore.model;

import java.util.List;

public class AllBookData {

    private Book book;

    private List<Author> authors;
    private List<Picture> pictures;

    public List<Author> getAuthors() {
        return authors;
    }

    public List<Picture> getPictures() {
        return pictures;
    }

    // setters
    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
