package com.library.lms.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Data
@Entity
@Table(name = "borrows")
public class Borrow {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int borrowID;

    private Date borrowDate;
    private Date dueDate;

    @ManyToOne
    @JoinColumn(name = "members_memberID")
    private Member memberI;


    @ManyToOne
    @JoinColumn(name = "bookID")
    private Books books;

    public Borrow(){}

    public Borrow(int borrowID, Date borrowDate, Date dueDate, Member memberI, Books books) {
        this.borrowID = borrowID;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.memberI = memberI;
        this.books = books;
    }
}
