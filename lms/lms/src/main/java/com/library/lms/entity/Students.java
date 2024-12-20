package com.library.lms.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@Entity
@Table(name = "students")
public class Students {

    //private int studentID;

    @Id
    @Column(name = "schoolStudentID")
    private int schoolStudentID;

    @Column(name = "course")
    private String course;

    @Column(name = "yearLevel")
    private int yearLevel;

    @Column(name = "department")
    private String department;

    @OneToOne
    @JoinColumn(name = "members_memberID", nullable = false)
    private Member memberI;
}
