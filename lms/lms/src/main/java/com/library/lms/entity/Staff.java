package com.library.lms.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Table(name = "staff")
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int staffID;

    private String position;
    private String shift;
    private String staffStatus;

    @OneToOne
    @JoinColumn(name = "members_memberID", nullable = false)
    private Member memberI;

    public Staff() {

    }
}
