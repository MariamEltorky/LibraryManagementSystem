package com.maids.cc.LibraryManagementSystem.repositories;

import com.maids.cc.LibraryManagementSystem.models.JPA.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepo extends JpaRepository<Book, Integer> {
}
