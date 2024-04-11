package com.maids.cc.LibraryManagementSystem.models.JPA;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PATRON")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Patron {

    @Id
    @Column(name = "ID")
    private int id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "CONTACTINFORMATION")
    private String contactInformation;
}
