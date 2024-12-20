package com.library.lms.service;

import com.library.lms.DTO.AttendanceRequest;
import com.library.lms.DTO.ToDashboardDTO;
import com.library.lms.entity.Attendance;
import com.library.lms.entity.Member;
import com.library.lms.repository.AttendanceRepository;
import com.library.lms.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private MemberRepository memberRepository;

    public ToDashboardDTO recordAttendance(AttendanceRequest request) {
        // Step 1: Identify the member based on memberType and identifier
        Member member = findMember(request.getMemberType(), request.getIdentifier(), request.getPosition());
        if (member == null) {
            // Return a ToDashboardDTO with a specific message or status
            return ToDashboardDTO.builder()
                    .status("Member not found.")
                    .build();
        }

        // Step 2: Record attendance
        return processAttendance(member, request);
    }

    private Member findMember(String memberType, String identifier, String position) {
        if ("student".equalsIgnoreCase(memberType)) {
            return memberRepository.findBySchoolStudentID(identifier);
        } else if ("staff".equalsIgnoreCase(memberType)) {
            String[] nameParts = identifier.split(", "); // e.g., "Doe, John" → ["Doe", "John"]
            if (nameParts.length < 2) {
                throw new IllegalArgumentException("Full name must be in 'LastName, FirstName' format.");
            }
            String lastName = nameParts[0];
            String firstName = nameParts[1];
            return memberRepository.findByFullNameAndPosition(lastName, firstName, position);
        }
        throw new IllegalArgumentException("Invalid member type.");
    }

    public ToDashboardDTO processAttendance(Member member, AttendanceRequest request) {
        LocalDate today = LocalDate.now();
        String memberType = request.getMemberType();
        String identifier = request.getIdentifier();
        LocalTime timeIn = request.getTimeIn();
        LocalTime timeOut = request.getTimeOut();
        String status = (timeOut == null) ? "time-in" : "time-out";  // Set status based on the action (time-in or time-out)

        // Find the latest attendance record for this member on the current date
        Attendance latestAttendance = attendanceRepository.findLatestAttendanceForMemberAndDate(member, today);

        // Check if there's no attendance record or the previous one is closed, and create a new one for time-in
        if (latestAttendance == null || latestAttendance.getTimeOut() != null) {
            // If no attendance record or previous one is closed, create a new one (time-in)
            Attendance newAttendance = Attendance.builder()
                    .memberI(member)
                    .date(today)
                    .timeIn(timeIn != null ? timeIn : LocalTime.now())  // Use timeIn if provided, otherwise use current time
                    .purpose(request.getPurpose())
                    .build();
            attendanceRepository.save(newAttendance);
        } else {
            // If an open attendance record exists, update it with time-out
            latestAttendance.setTimeOut(LocalTime.now()); // Automatically use LocalTime.now() for time-out
            attendanceRepository.save(latestAttendance);
        }

        // Return the ToDashboardDTO containing the processed attendance data
        return ToDashboardDTO.builder()
                .memberType(memberType)
                .identifier(identifier)
                .position(request.getPosition()) // Optional, for staff
                .purpose(request.getPurpose())   // Reason for attendance
                .timeIn(latestAttendance != null ? latestAttendance.getTimeIn() : LocalTime.now())  // Time in, from the attendance record, default to current time if null
                .timeOut(latestAttendance != null ? latestAttendance.getTimeOut() : null) // Time out, from the attendance record
                .status(status)  // "time-in" or "time-out"
                .build();
    }

}
