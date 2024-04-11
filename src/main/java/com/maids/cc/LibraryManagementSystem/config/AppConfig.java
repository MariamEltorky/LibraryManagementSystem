package com.maids.cc.LibraryManagementSystem.config;

import com.maids.cc.LibraryManagementSystem.models.JPA.Book;
import com.maids.cc.LibraryManagementSystem.models.JPA.BorrowingRecord;
import com.maids.cc.LibraryManagementSystem.models.JPA.Patron;
import com.maids.cc.LibraryManagementSystem.repositories.BookRepo;
import com.maids.cc.LibraryManagementSystem.repositories.BorrowingRecordRepo;
import com.maids.cc.LibraryManagementSystem.repositories.PatronRepo;
import com.maids.cc.LibraryManagementSystem.services.BorrowingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableAspectJAutoProxy
@EnableTransactionManagement
public class AppConfig {

    static Logger logger = LogManager.getLogger(AppConfig.class);

    @Autowired
    BookRepo bookRepo;

    @Autowired
    PatronRepo patronRepo;

    @Autowired
    BorrowingRecordRepo borrowingRecordRepo;

    public static Map<Integer, Book> bookMap = new HashMap<>();
    public static Map<Integer, Patron> patronMap = new HashMap<>();
    public static Map<Integer, BorrowingRecord> borrowingRecordMap = new HashMap<>();

    @Bean
    public Map<Integer, Book> booksBean(){
        logger.info("AppConfig - Start executing booksBean");
        List<Book> bookList=bookRepo.findAll();
        bookList.stream().forEach(book->{
            bookMap.put(book.getId(),book);
        });
        logger.info("Loaded Books - {}",bookMap.toString());

        logger.info("AppConfig - End executing booksBean");
        return bookMap;
    }

    @Bean
    public Map<Integer, Patron> patronBean(){
        logger.info("AppConfig - Start executing patronBean");
        List<Patron> patronList=patronRepo.findAll();
        patronList.stream().forEach(patron->{
            patronMap.put(patron.getId(),patron);
        });
        logger.info("Loaded patrons - {}",patronMap.toString());

        logger.info("AppConfig - End executing patronBean");
        return patronMap;
    }

    @Bean
    public Map<Integer, BorrowingRecord> borrowingRecordBean(){
        logger.info("AppConfig - Start executing borrowingRecordBean");
        List<BorrowingRecord> borrowingList=borrowingRecordRepo.findAll();
        borrowingList.stream().forEach(borrowing->{
            borrowingRecordMap.put(borrowing.getID(),borrowing);
        });
        logger.info("Loaded borrowing Records - {}",borrowingRecordMap.toString());

        logger.info("AppConfig - End executing borrowingRecordBean");
        return borrowingRecordMap;
    }

    @Bean
    public Logger logger() {
        return LogManager.getLogger(BorrowingService.class);
    }
}
