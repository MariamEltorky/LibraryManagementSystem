package com.maids.cc.LibraryManagementSystem;

import com.maids.cc.LibraryManagementSystem.models.JPA.Book;
import com.maids.cc.LibraryManagementSystem.models.JPA.Patron;
import com.maids.cc.LibraryManagementSystem.models.JPA.BorrowingRecord;
import com.maids.cc.LibraryManagementSystem.repositories.BorrowingRecordRepo;
import com.maids.cc.LibraryManagementSystem.services.BookService;
import com.maids.cc.LibraryManagementSystem.services.BorrowingService;
import com.maids.cc.LibraryManagementSystem.services.PatronService;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BorrowingServiceTest {
    @Mock
    private BorrowingRecordRepo borrowingRecordRepo;
    @Mock
    private BookService bookService;
    @Mock
    private PatronService patronService;
    @Mock
    private Logger logger;
    @InjectMocks
    private BorrowingService borrowingService;

    @Test
    public void testBorrowBook() {
        int bookId = 1;
        int patronId = 1;
        Book book = new Book();
        book.setBorrowed(0);
        Patron patron = new Patron();
        when(bookService.findBookById(bookId)).thenReturn(book);
        when(patronService.findPatronById(patronId)).thenReturn(patron);
        when(bookService.saveBook(book)).thenReturn(book);
        Book borrowedBook = borrowingService.borrowBook(bookId, patronId);
        assertNotNull(borrowedBook);
        assertEquals(1, borrowedBook.getBorrowed());
        assertEquals(patron, borrowedBook.getPatron());
    }

    @Test
    public void testBorrowBook_BookNotFound() {
        int bookId = 1;
        int patronId = 1;
        when(bookService.findBookById(bookId)).thenReturn(null);
        Book borrowedBook = borrowingService.borrowBook(bookId,patronId);
        assertNull(borrowedBook);
        verify(logger).info("Book not found");
    }

    @Test
    public void testBorrowBook_PatronNotFound() {
        int bookId = 1;
        int patronId = 1;
        Book book = new Book();
        Patron patron = new Patron();
        when(bookService.findBookById(bookId)).thenReturn(book);
        when(patronService.findPatronById(patronId)).thenReturn(null);
        Book borrowedBook = borrowingService.borrowBook(bookId,patronId);
        assertNull(borrowedBook);
        verify(logger).info("Patron not found");
    }

    @Test
    public void testBorrowBook_BookBorrowed() {
        int bookId = 1;
        int patronId = 1;
        Book book = new Book();
        book.setBorrowed(1);
        Patron patron = new Patron();
        when(bookService.findBookById(bookId)).thenReturn(book);
        when(patronService.findPatronById(patronId)).thenReturn(patron);
        Book borrowedBook = borrowingService.borrowBook(bookId,patronId);
        assertNull(borrowedBook);
        verify(logger).info("Book already borrowed");
    }

    @Test
    public void testSaveBorrowingRecord() {
        BorrowingRecord borrowingRecord = new BorrowingRecord();
        when(borrowingRecordRepo.save(borrowingRecord)).thenReturn(borrowingRecord);
        BorrowingRecord savedRecord = borrowingService.saveBorrowingRecord(borrowingRecord);
        assertNotNull(savedRecord);
    }

    @Test
    public void testReturnBook() {
        int bookId = 1;
        Book book = new Book();
        book.setBorrowed(1);
        when(bookService.findBookById(bookId)).thenReturn(book);
        when(bookService.saveBook(book)).thenReturn(book);
        Book returnedBook = borrowingService.returnBook(bookId);
        assertNotNull(returnedBook);
        assertEquals(0, returnedBook.getBorrowed());
        assertNull(returnedBook.getPatron());
    }

    @Test
    public void testReturnBook_BookNotFound() {
        int bookId = 1;
        when(bookService.findBookById(bookId)).thenReturn(null);
        Book returnedBook = borrowingService.returnBook(bookId);
        assertNull(returnedBook);
        verify(logger).info("Book not found");
    }

    @Test
    public void testReturnBook_BookNotBorrowed() {
        int bookId = 1;
        Book book = new Book();
        book.setBorrowed(0);
        when(bookService.findBookById(bookId)).thenReturn(book);
        Book returnedBook = borrowingService.returnBook(bookId);
        assertNull(returnedBook);
        verify(logger).info("Book not borrowed");
    }

    @Test
    public void testReturnBorrowedBook() {
        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setID(1);
        doNothing().when(borrowingRecordRepo).deleteById(borrowingRecord.getID());
        borrowingService.returnBorrowedBook(borrowingRecord);
        verify(borrowingRecordRepo).deleteById(borrowingRecord.getID());
    }

}
