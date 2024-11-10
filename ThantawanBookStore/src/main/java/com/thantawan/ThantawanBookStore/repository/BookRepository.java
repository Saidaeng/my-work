package com.thantawan.ThantawanBookStore.repository;

import com.thantawan.ThantawanBookStore.model.*;

import java.util.List;

public interface BookRepository {
    boolean existsByBookID(String bookID);

    String addBook(BookForm bookForm);

    Member findByMemberID(String memberID);

    Category findByCategoryID(String categoryID);

    List<Book> getRandomBook();

    List<Book> getBookByLessorID(String lessorID);

    Integer countBook();

    Integer countBookByID(String lessorID);

    List<Author> findAuthorsByBookID(String bookID);

    List<Picture> findPicturesByBookID(String bookID);

    Book getBookByID(String bookID);
}
