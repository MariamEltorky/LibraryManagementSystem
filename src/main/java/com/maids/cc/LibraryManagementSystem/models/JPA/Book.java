package com.maids.cc.LibraryManagementSystem.models.JPA;

import javax.persistence.*;

import lombok.*;
import java.sql.Date.*;


@Entity
@Table(name = "BOOK")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @Column(name = "ID")
    private int id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "AUTHOR")
    private String author;

    @Column(name = "PUBLICATIONYEAR")
    private String  publicationYear;

    @Column(name = "ISBN")
    private String ISBN;

    @Column(name = "BORROWED")
    private int borrowed;

    @ManyToOne
    @JoinColumn(name = "patron")
    private Patron patron ;


}
