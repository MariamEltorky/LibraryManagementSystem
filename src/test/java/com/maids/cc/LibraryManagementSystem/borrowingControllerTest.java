package com.maids.cc.LibraryManagementSystem;

import com.maids.cc.LibraryManagementSystem.controller.BookController;
import com.maids.cc.LibraryManagementSystem.controller.borrowingController;
import com.maids.cc.LibraryManagementSystem.models.BorrowingRequest.BorrowingRequestBody;
import com.maids.cc.LibraryManagementSystem.models.JPA.Book;
import com.maids.cc.LibraryManagementSystem.models.JPA.BorrowingRecord;
import com.maids.cc.LibraryManagementSystem.services.BorrowingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class borrowingControllerTest {

    @Mock
    private BorrowingService borrowingService;

    @InjectMocks
    private borrowingController borrowingController;

    @Test
    public void testBorrowBook() {
        int bookId = 1000;
        int patronId = 2007;
        BorrowingRequestBody requestBody = new BorrowingRequestBody(5000,bookId, patronId ,"2024-04-08" , "2024-04-15");
        Book borrowedBook = new Book(1000, "The Adventures of Huckleberry Finn", "Hannah Ritchie", "2023", "978-3161484104", 0, null);
        BorrowingRecord borrowingRecord = new BorrowingRecord(5000 , 1000 , 2002 , "2024-04-08" , "2024-04-15");
        when(borrowingService.borrowBook(bookId, patronId)).thenReturn(borrowedBook);
        when(borrowingService.extractBorrowingData(requestBody)).thenReturn(borrowingRecord);
        ResponseEntity<String> response = borrowingController.borrowBook(bookId, patronId, requestBody);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(borrowingService).saveBorrowingRecord(borrowingRecord);
    }

    @Test
    public void testBorrowBook_DifferentIDs() {
        int bookID = 1000;
        int patronID = 2007;
        int anotherID = 6000;
        BorrowingRequestBody requestBody = new BorrowingRequestBody(5000,bookID, patronID ,"2024-04-08" , "2024-04-15");

        ResponseEntity<String> response = borrowingController.borrowBook(bookID, anotherID, requestBody);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(" Please Enter a correct BookID and PatronID", response.getBody()); // Update with expected error message
    }

    @Test
    public void testBorrowBook_BookNotAvailable() {
        int bookID = 1000;
        int patronID = 2007;
        BorrowingRequestBody requestBody = new BorrowingRequestBody(5000,bookID, patronID ,"2024-04-08" , "2024-04-15");
        when(borrowingService.borrowBook(bookID, patronID)).thenReturn(null);

        ResponseEntity<String> response = borrowingController.borrowBook(bookID, patronID, requestBody);

        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
        assertEquals("Book not found or Patron not found or Book already borrowed", response.getBody()); // Update with expected error message
    }
}
