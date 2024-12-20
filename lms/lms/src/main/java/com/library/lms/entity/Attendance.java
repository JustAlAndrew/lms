package com.library.lms.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;


import java.time.LocalDate;
import java.time.LocalTime;


@Data
@Builder
@Entity
@Table(name = "attendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int attendanceID;

    @ManyToOne
    @JoinColumn(name = "memberID", referencedColumnName = "memberID")
    private Member memberI;

    private LocalDate date;
    private LocalTime timeIn;
    private LocalTime timeOut;
    private String purpose;

    // Default constructor
    public Attendance() {
        // This can be empty or you can initialize any default values if necessary.
    }

    public Attendance(int attendanceID, Member memberI, LocalDate date, LocalTime timeIn, LocalTime timeOut, String purpose) {
        this.attendanceID = attendanceID;
        this.memberI = memberI;
        this.date = date;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        this.purpose = purpose;
    }
}
