package com.maids.cc.LibraryManagementSystem.repositories;

import com.maids.cc.LibraryManagementSystem.models.JPA.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepo  extends JpaRepository<Users, Integer> {

    Users findByUsername (String userName);
}
