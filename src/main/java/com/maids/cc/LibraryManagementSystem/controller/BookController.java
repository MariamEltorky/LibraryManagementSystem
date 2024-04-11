package com.maids.cc.LibraryManagementSystem.controller;

import com.maids.cc.LibraryManagementSystem.models.BookRequest.BookRequestBody;
import com.maids.cc.LibraryManagementSystem.models.JPA.Book;
import com.maids.cc.LibraryManagementSystem.services.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/books")
@AllArgsConstructor
public class BookController {

    private static final Logger logger = Logger.getLogger(BookController.class.getName());
    private final BookService bookService;


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Book> getAllBooks() {
        return bookService.findAllBooks();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> getBookByID(@PathVariable int id) {
        Book book = bookService.findBookById(id);
        if (book != null) {
            String jsonBook = bookService.convertToJson(book);
            return ResponseEntity.status(HttpStatus.OK).body(jsonBook);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found, Please Enter a correct BookID");
        }
    }

    @PostMapping
    public ResponseEntity<String> addNewBook(@Valid @RequestBody BookRequestBody bookRequestBody) {
        Book book=bookService.extractBookData(bookRequestBody);
        bookService.saveBook(book);
        return ResponseEntity.status(HttpStatus.OK).body("Book Added Successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateBook(@PathVariable int id, @Valid @RequestBody BookRequestBody bookRequestBody) {
        if (id == bookRequestBody.getId()) {
            Book book=bookService.extractBookData(bookRequestBody);
            bookService.saveBook(book);
            return ResponseEntity.status(HttpStatus.OK).body("Book Updated successfully");
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please Enter a correct BookID");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable int id) {
        Book book = bookService.findBookById(id);
        if (book != null) {
            bookService.deleteBookById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Book Deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found, Please Enter a correct BookID");
        }
    }

}
