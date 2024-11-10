package com.thantawan.ThantawanBookStore.controllers;

import com.thantawan.ThantawanBookStore.model.AllBookData;
import com.thantawan.ThantawanBookStore.model.Book;
import com.thantawan.ThantawanBookStore.model.BookForm;
import com.thantawan.ThantawanBookStore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/book")
public class BookControllers {

    @Autowired
    private BookRepository bookRepository;

    @PostMapping("/addBook")
    public ResponseEntity<Map<String, Object>> addBook(@ModelAttribute BookForm bookForm) {
        Map<String, Object> response = new HashMap<>();

        String result = bookRepository.addBook(bookForm);

        if ("Successfully add book".equals(result)) {
            response.put("success", true);
            response.put("book", bookForm);
        } else {
            response.put("success", false);
            response.put("message", result);
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/getRandomBook")
    public List<AllBookData> getRandomBook(){
        List<Book> randomBook = bookRepository.getRandomBook();
        List<AllBookData> result = new ArrayList<AllBookData>();
        for(Book book : randomBook){
            AllBookData books = new AllBookData();
            books.setBook(book);
            books.setAuthors(bookRepository.findAuthorsByBookID(book.getBookID()));
            books.setPictures(bookRepository.findPicturesByBookID(book.getBookID()));
            result.add(books);
        }
        return result;
    }

    @PostMapping("/getBookByID")
    public List<AllBookData> getBookByLessorID(@RequestBody String lessorID){
        lessorID = lessorID.split(":")[1].replaceAll("[^0-9]", "");
        List<Book> getBook = bookRepository.getBookByLessorID(lessorID);
        List<AllBookData> result = new ArrayList<AllBookData>();
        for(Book book : getBook){
            AllBookData books = new AllBookData();
            books.setBook(book);
            books.setAuthors(bookRepository.findAuthorsByBookID(book.getBookID()));
            books.setPictures(bookRepository.findPicturesByBookID(book.getBookID()));
            result.add(books);
        }
        return result;
    }

    @PostMapping("/getBookByBookID")
    public AllBookData getBookByBookID(@RequestBody String bookID){
        bookID = bookID.split(":")[1].replaceAll("[^0-9]", "");
        Book getBook = bookRepository.getBookByID(bookID);
        AllBookData result = new AllBookData();
        result.setBook(getBook);
        result.setAuthors(bookRepository.findAuthorsByBookID(bookID));
        result.setPictures(bookRepository.findPicturesByBookID(bookID));
        return result;
    }


    @PostMapping("/countBook")
    public Integer countBook(){
        return bookRepository.countBook();
    }

    @PostMapping("/countBookByID")
    public Integer countBookByLessorID(@RequestBody String lessorID){
        lessorID = lessorID.split(":")[1].replaceAll("[^0-9]", "");
        return bookRepository.countBookByID(lessorID);
    }

}
