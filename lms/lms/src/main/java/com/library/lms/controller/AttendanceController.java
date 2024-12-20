package com.library.lms.controller;

import com.library.lms.DTO.AttendanceRequest;
import com.library.lms.DTO.ToDashboardDTO;
import com.library.lms.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    // Adjust the return type to ToDashboardDTO
    @PostMapping("/time-in")
    public ToDashboardDTO timeIn(@RequestBody AttendanceRequest request) {
        // Call the service method which now returns a ToDashboardDTO
        return attendanceService.recordAttendance(request);
    }

    // Adjust the return type to ToDashboardDTO
    @PostMapping("/time-out")
    public ToDashboardDTO timeOut(@RequestBody AttendanceRequest request) {
        // Call the service method which now returns a ToDashboardDTO
        return attendanceService.recordAttendance(request);
    }

}
