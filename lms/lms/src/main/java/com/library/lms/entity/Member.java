package com.library.lms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;

@Data
@Entity
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer memberID;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "middleName")
    private String middleName;

    @Column(name = "extensionName")
    private String extensionName;

    @Column(name = "email")
    private String email;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "memberType")
    private String memberType;

    /*@OneToMany(mappedBy = "memberI")
    private List<Attendance>attendances;

    @OneToMany(mappedBy = "memberI")
    private List<Borrow>borrows;*/

    @JsonIgnore
    @OneToOne(mappedBy = "memberI", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Students student;

    @JsonIgnore
    @OneToOne(mappedBy = "memberI", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Staff staff;

}
