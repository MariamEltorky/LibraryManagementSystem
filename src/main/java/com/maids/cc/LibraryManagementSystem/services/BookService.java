package com.maids.cc.LibraryManagementSystem.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maids.cc.LibraryManagementSystem.models.BookRequest.BookRequestBody;
import com.maids.cc.LibraryManagementSystem.models.JPA.Book;
import com.maids.cc.LibraryManagementSystem.models.JPA.Patron;
import com.maids.cc.LibraryManagementSystem.repositories.BookRepo;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class BookService {
    static Logger logger = LogManager.getLogger(BookService.class);

    private final BookRepo bookRepo;

//    @Cacheable("books")
    public List<Book> findAllBooks() {
        return bookRepo.findAll();
    }

    public Book findBookById(int id) {
        Book book= bookRepo.findById(id).orElse(null);
        return book;
    }
    @Transactional(rollbackFor = {Exception.class})
    public Book saveBook(Book book) {
        return bookRepo.save(book);
    }
    @Transactional
    public void deleteBookById(int id) {
        bookRepo.deleteById(id);
    }

    public Book extractBookData(BookRequestBody bookRequestBody) {
        int id= bookRequestBody.getId();
        String title = bookRequestBody.getTitle();
        String author= bookRequestBody.getAuthor();
        String publicationYear = bookRequestBody.getPublicationYear();
        String ISBN = bookRequestBody.getISBN();
        int borrowed = bookRequestBody.getBorrowed();
        Patron patron = bookRequestBody.getPatron();
        Book book=new Book(id , title , author , publicationYear ,ISBN ,  borrowed , patron);
        return book;
    }

    public String convertToJson(Book book) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(book);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
