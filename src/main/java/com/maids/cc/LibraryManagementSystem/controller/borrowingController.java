package com.maids.cc.LibraryManagementSystem.controller;

import com.maids.cc.LibraryManagementSystem.models.BorrowingRequest.BorrowingRequestBody;
import com.maids.cc.LibraryManagementSystem.models.JPA.Book;
import com.maids.cc.LibraryManagementSystem.models.JPA.BorrowingRecord;
import com.maids.cc.LibraryManagementSystem.services.BorrowingService;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class borrowingController {

    static Logger logger = LogManager.getLogger(borrowingController.class);
    private final BorrowingService borrowingService;


    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<String> borrowBook(@PathVariable int bookId, @PathVariable int patronId,@Valid @RequestBody BorrowingRequestBody borrowingRequestBody) {
        logger.info("borrowingRequestBody - {}",borrowingRequestBody);
        if (borrowingRequestBody.getBookID() == bookId && borrowingRequestBody.getPatronID() == patronId) {
            Book borrowedBook = borrowingService.borrowBook(bookId, patronId);
            if (borrowedBook != null) {
                BorrowingRecord borrowingRecord=borrowingService.extractBorrowingData(borrowingRequestBody);
                borrowingService.saveBorrowingRecord(borrowingRecord);
                return ResponseEntity.status(HttpStatus.OK).body("" +borrowedBook);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Book not found or Patron not found or Book already borrowed");
            }
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(" Please Enter a correct BookID and PatronID");
        }
    }

    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<String> returnBook(@PathVariable int bookId,@Valid @RequestBody BorrowingRequestBody borrowingRequestBody) {
        if (borrowingRequestBody.getBookID() == bookId) {
            Book returnedBook = borrowingService.returnBook(bookId);
            if (returnedBook != null) {
                BorrowingRecord borrowingRecord=borrowingService.extractBorrowingData(borrowingRequestBody);
                borrowingService.returnBorrowedBook(borrowingRecord);
                return ResponseEntity.status(HttpStatus.OK).body("" +returnedBook);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Book not found or Book not borrowed");
            }
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(" Please Enter a correct BookId");
        }
    }
}
