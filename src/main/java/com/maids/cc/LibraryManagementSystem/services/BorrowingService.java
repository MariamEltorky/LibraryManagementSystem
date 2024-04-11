package com.maids.cc.LibraryManagementSystem.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maids.cc.LibraryManagementSystem.models.BookRequest.BookRequestBody;
import com.maids.cc.LibraryManagementSystem.models.BorrowingRequest.BorrowingRequestBody;
import com.maids.cc.LibraryManagementSystem.models.JPA.Book;
import com.maids.cc.LibraryManagementSystem.models.JPA.BorrowingRecord;
import com.maids.cc.LibraryManagementSystem.models.JPA.Patron;
import com.maids.cc.LibraryManagementSystem.repositories.BookRepo;
import com.maids.cc.LibraryManagementSystem.repositories.BorrowingRecordRepo;
import com.maids.cc.LibraryManagementSystem.repositories.PatronRepo;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Random;

@Service
@AllArgsConstructor
public class BorrowingService {
    private final Logger logger;
    private final BorrowingRecordRepo borrowingRecordRepo;
    private final BookService bookService;
    private final PatronService patronService;

    public Book borrowBook(int bookId, int patronId) {
        Book book=bookService.findBookById(bookId);
        Patron patron = patronService.findPatronById(patronId);
        Book bookToBorrow = null;
        if (book == null) {
            logger.info("Book not found");
        }
        else if (patron == null) {
            logger.info("Patron not found");
        }
        else if (book.getBorrowed()==1) {
            logger.info("Book already borrowed");
        }
        else if (book != null && patron != null && book.getBorrowed()==0 ) {
            book.setPatron(patron);
            book.setBorrowed(1);
            bookToBorrow = bookService.saveBook(book);
        }
        return bookToBorrow;
    }

    @Transactional(rollbackFor = {Exception.class})
    public BorrowingRecord saveBorrowingRecord(BorrowingRecord borrowingRecord) {
        return borrowingRecordRepo.save(borrowingRecord);
    }

    public Book returnBook(int bookId) {
        Book book = bookService.findBookById(bookId);
        Book bookToReturn = null;
        if (book == null) {
            logger.info("Book not found");
        }
        else if (book.getBorrowed()==0) {
            logger.info("Book not borrowed");
        }
        else if (book != null && book.getBorrowed()==1 ) {
            book.setPatron(null);
            book.setBorrowed(0);
            bookToReturn=bookService.saveBook(book);
        }
        return bookToReturn;
    }

    @Transactional
    public void returnBorrowedBook(BorrowingRecord borrowingRecord) {
        borrowingRecordRepo.deleteById(borrowingRecord.getID());
    }

    public BorrowingRecord extractBorrowingData(BorrowingRequestBody borrowingRequestBody) {
        int id= borrowingRequestBody.getId();
        int bookID = borrowingRequestBody.getBookID();
        int patronID= borrowingRequestBody.getPatronID();
        String borrowingDate = borrowingRequestBody.getBorrowingDate();
        String returnDate = borrowingRequestBody.getReturnDate();
        BorrowingRecord borrowingRecord=new BorrowingRecord(id , bookID , patronID , borrowingDate ,returnDate );
        return borrowingRecord;
    }

}
