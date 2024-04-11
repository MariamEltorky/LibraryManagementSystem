package com.maids.cc.LibraryManagementSystem;

import com.maids.cc.LibraryManagementSystem.models.JPA.Book;
import com.maids.cc.LibraryManagementSystem.models.JPA.BorrowingRecord;
import com.maids.cc.LibraryManagementSystem.models.JPA.Patron;
import com.maids.cc.LibraryManagementSystem.repositories.BookRepo;
import com.maids.cc.LibraryManagementSystem.repositories.BorrowingRecordRepo;
import com.maids.cc.LibraryManagementSystem.repositories.PatronRepo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RepositoriesTest {

    static Logger logger = LogManager.getLogger(RepositoriesTest.class);

    @Autowired
    private BookRepo bookRepo;
    @Autowired
    private BorrowingRecordRepo borrowingRecordRepo;
    @Autowired
    private PatronRepo patronRepo;

    @Test
    public void findAllBook() {
        List<Book> bookList = this.bookRepo.findAll();
        logger.info("RepositoriesTest - findAllBook - Book Size - {}", bookList.size());
//        assertEquals(4, bookList.size());
        assertEquals(1000, bookList.get(0).getId());
    }

    @Test
    public void findAllPatron() {
        List<Patron> patronList = this.patronRepo.findAll();
        logger.info("RepositoriesTest - findAllPatron - Patron Size - {}", patronList.size());
//        assertEquals(3, patronList.size());
        assertEquals(2002, patronList.get(0).getId());
    }

    @Test
    public void findAllBorrowing() {
        List<BorrowingRecord> borrowingList = this.borrowingRecordRepo.findAll();
        logger.info("RepositoriesTest - findAllBorrowing - Borrowing Size - {}", borrowingList.size());
        assertEquals(1, borrowingList.size());
        assertEquals(3500, borrowingList.get(0).getID());
    }

}
