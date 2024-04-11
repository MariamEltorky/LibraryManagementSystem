package com.maids.cc.LibraryManagementSystem.repositories;

import com.maids.cc.LibraryManagementSystem.models.JPA.BorrowingRecord;
import com.maids.cc.LibraryManagementSystem.models.JPA.Patron;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowingRecordRepo extends JpaRepository<BorrowingRecord, Integer> {
}
