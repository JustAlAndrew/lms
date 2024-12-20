package com.library.lms.DTO;


import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;

@Data
@Builder
public class AttendanceRequest {

    private String memberType;    // "student" or "staff"
    private String identifier;    // schoolStudentID for students, fullName for staff
    private String position;      // Optional, only for staff
    private String purpose;       // Reason for attendance
    private LocalTime timeIn;     // Optional: Override for time-in
    private LocalTime timeOut;    // Optional: Override for time-out

}
