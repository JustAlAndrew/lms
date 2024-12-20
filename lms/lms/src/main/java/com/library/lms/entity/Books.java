package com.library.lms.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "books")
public class Books {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookID;

    private String title;
    private String author;
    private String category;
    private boolean isAvailable = true;

    public Books() {
    }


    public Books(int bookID, String title, String author, String category, boolean isAvailable, List<Borrow> borrows) {
        this.bookID = bookID;
        this.title = title;
        this.author = author;
        this.category = category;
        this.isAvailable = isAvailable;
       // this.borrows = borrows;
    }
}
