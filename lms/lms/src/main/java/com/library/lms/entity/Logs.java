package com.library.lms.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "logs")
public class Logs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int logID;

    private String actionType;
    private Date actionDate;


    @ManyToOne
    @JoinColumn(name = "books_bookID")
    private Books bookI;

    @ManyToOne
    @JoinColumn(name = "members_memberID")
    private Member memberI;

    public Logs(){}

    public Logs(int logID, String actionType, Date actionDate, Books bookI, Member memberI) {
        this.logID = logID;
        this.actionType = actionType;
        this.actionDate = actionDate;
        this.bookI = bookI;
        this.memberI = memberI;
    }
}
