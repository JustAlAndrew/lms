package com.library.lms.DTO;

import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;

@Data
@Builder
public class ToDashboardDTO {

    private String memberType;    // "student" or "staff"
    private String identifier;    // schoolStudentID or fullName
    private String position;      // Optional, only for staff
    private String purpose;       // Reason for attendance
    private LocalTime timeIn;     // Time-in, can be optional if you're returning it for display
    private LocalTime timeOut;    // Time-out, will be set when timing out
    private String status;        // "time-in" or "time-out", can be displayed in the dashboard

}
