package com.maids.cc.LibraryManagementSystem;

import com.maids.cc.LibraryManagementSystem.controller.BookController;
import com.maids.cc.LibraryManagementSystem.models.BookRequest.BookRequestBody;
import com.maids.cc.LibraryManagementSystem.models.JPA.Book;
import com.maids.cc.LibraryManagementSystem.services.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BookControllerTest {


    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @Test
    public void testGetAllBooks() {
        List<Book> books = Arrays.asList(
                new Book(1000, "The Adventures of Huckleberry Finn", "Hannah Ritchie", "2023", "978-3161484104", 0, null),
                new Book(1009, "World", "Ritchie", "1945", "978-3161487895", 0, null),
                new Book(1010, "Pride and Prejudice", "Jane Austen", "1975", "978-3161483456", 1, null)
        );
        when(bookService.findAllBooks()).thenReturn(books);
//        List<Book> result = bookController.getAllBooks();
//        assertEquals(books, result);
    }

    @Test
    public void testGetBookByID() {
        int bookID = 1;
        String expectedJson = "{\"id\":1000,\"title\":\"World\",\"author\":\"Ritchie\",\"publicationYear\":\"2023\",\"ISBN\":\"978-3161484104\",\"borrowed\":0,\"patron\":null}";
        Book book=new Book(1000, "World", "Ritchie", "2023", "978-3161484104", 0, null);
        when(bookService.findBookById(bookID)).thenReturn(book);
        when(bookService.convertToJson(book)).thenReturn(expectedJson);
        ResponseEntity<String> response = bookController.getBookByID(bookID);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedJson, response.getBody());
    }

    @Test
    public void testGetBookByID_BookNotFound() {
        int bookID = 1000;
        when(bookService.findBookById(bookID)).thenReturn(null);
        ResponseEntity<String> response = bookController.getBookByID(bookID);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Book not found, Please Enter a correct BookID", response.getBody());
    }

    @Test
    public void testAddNewBook() {
        BookRequestBody bookRequestBody = new BookRequestBody(1000, "World", "Ritchie", "2023", "978-3161484104", 0, null);
        Book book = new Book(1000, "World", "Ritchie", "2023", "978-3161484104", 0, null);
        when(bookService.extractBookData(bookRequestBody)).thenReturn(book);
        ResponseEntity<String> response = bookController.addNewBook(bookRequestBody);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Book Added Successfully", response.getBody());
        verify(bookService).saveBook(book);
    }
    @Test
    public void testUpdateBook() {
        int bookID = 1000;
        BookRequestBody bookRequestBody = new BookRequestBody(bookID, "World", "Ritchie", "2023", "978-3161484104", 0, null);
        Book book = new Book(bookID, "World", "Ritchie", "2023", "978-3161484104", 0, null);
        when(bookService.extractBookData(bookRequestBody)).thenReturn(book);
        ResponseEntity<String> response = bookController.updateBook(bookID, bookRequestBody);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Book Updated successfully", response.getBody());
        verify(bookService).saveBook(book);
    }
    @Test
    public void testUpdateBook_DifferentId() {
        int bookID = 1000;
        int anotherID = 2000;
        BookRequestBody bookRequestBody = new BookRequestBody(anotherID, "World", "Ritchie", "2023", "978-3161484104", 0, null);
        ResponseEntity<String> response = bookController.updateBook(bookID, bookRequestBody);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(bookService, never()).saveBook(any());
    }

    @Test
    public void testDeleteBook() {
        int bookID = 1000;
        Book existingBook = new Book(bookID , "World", "Ritchie", "2023", "978-3161484104", 0, null);
        when(bookService.findBookById(bookID)).thenReturn(existingBook);
        ResponseEntity<String> response = bookController.deleteBook(bookID);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Book Deleted successfully", response.getBody());
        verify(bookService).deleteBookById(bookID);
    }

    @Test
    public void testDeleteBook_BookNotFound() {
        int incorrectID = 2000;
        when(bookService.findBookById(incorrectID)).thenReturn(null);
        ResponseEntity<String> response = bookController.deleteBook(incorrectID);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Book not found, Please Enter a correct BookID", response.getBody());
        verify(bookService, never()).deleteBookById(anyInt());
    }
}
