package com.maids.cc.LibraryManagementSystem.models.JPA;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "BORROWINGRECORD")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BorrowingRecord {

    @Id
    @Column(name = "ID")
    private int ID;

    @Column(name = "BOOKID")
    private int bookID;

    @Column(name = "PATRONID")
    private int patronID;

    @Column(name = "BORROWINGDATE")
    private String borrowingDate;

    @Column(name = "RETURNDATE")
    private String returnDate;

}
