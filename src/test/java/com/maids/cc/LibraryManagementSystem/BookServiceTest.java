package com.maids.cc.LibraryManagementSystem;

import com.maids.cc.LibraryManagementSystem.models.BookRequest.BookRequestBody;
import com.maids.cc.LibraryManagementSystem.models.JPA.Book;
import com.maids.cc.LibraryManagementSystem.models.JPA.Patron;
import com.maids.cc.LibraryManagementSystem.repositories.BookRepo;
import com.maids.cc.LibraryManagementSystem.services.BookService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

    @Mock
    private BookRepo bookRepo;

    @InjectMocks
    private BookService bookService;

    @Before
    public void setUp() {
        List<Book> books = Arrays.asList(
                new Book(1000, "The Adventures of Huckleberry Finn", "Hannah Ritchie", "2023", "978-3161484104", 0, null),
                new Book(1009, "World", "Ritchie", "1945", "978-3161487895", 0, null),
                new Book(1010, "Pride and Prejudice", "Jane Austen", "1975", "978-3161483456", 1, null)
        );

        when(bookRepo.findAll()).thenReturn(books);
        when(bookRepo.findById(1000)).thenReturn(Optional.of(books.get(0)));
        when(bookRepo.save(any(Book.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    public void testFindAllBooks() {
        List<Book> bookList = bookService.findAllBooks();
        assertNotNull(bookList);
        assertEquals(3, bookList.size());
        assertEquals("The Adventures of Huckleberry Finn", bookList.get(0).getTitle());
        assertNotEquals("The Adventures", bookList.get(1).getAuthor());
    }

    @Test
    public void testFindBookById() {
        Book book = bookService.findBookById(1000);
        assertNotNull(book);
        assertEquals("The Adventures of Huckleberry Finn", book.getTitle());
        assertNotEquals("Jane Austen", book.getAuthor());
    }

    @Test
    public void testSaveBook() {
        Book bookToSave = new Book(1011, "Don Quixote", "Miguel de Cervantes", "2956", "978-1234567890", 0, null);
        Book savedBook = bookService.saveBook(bookToSave);
        assertNotNull(savedBook);
        assertEquals("Miguel de Cervantes", savedBook.getAuthor());
        verify(bookRepo, times(1)).save(bookToSave);
    }

    @Test
    public void testDeleteBookById() {
        bookService.deleteBookById(1010);
        verify(bookRepo, times(1)).deleteById(1010);
    }

    @Test
    public void testExtractBookData() {
        BookRequestBody requestBody = new BookRequestBody(1001, "Alice's Adventures in Wonderland", "Lewis Carroll", "1678", "978-1234567890", 0, null);
        Book extractedBook = bookService.extractBookData(requestBody);
        assertNotNull(extractedBook);
        assertEquals(1001, extractedBook.getId());
        assertEquals("Alice's Adventures in Wonderland", extractedBook.getTitle());
    }
}
