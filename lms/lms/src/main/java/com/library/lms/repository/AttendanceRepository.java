package com.library.lms.repository;

import com.library.lms.entity.Attendance;
import com.library.lms.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Component
@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {

    @Query("SELECT a FROM Attendance a WHERE a.memberI = :memberID AND a.date = :date ORDER BY a.timeIn DESC")
    Attendance findLatestAttendanceForMemberAndDate(@Param("memberID") Member member, @Param("date") LocalDate date);

  /*  @Query("SELECT a FROM Attendance a WHERE a.memberID = :memberID AND a.timeOut IS NULL ORDER BY a.timeIn DESC")
    Attendance findLatestByMemberIDAndTimeOutIsNull(@Param("memberID") Integer memberID);

    @Query("SELECT a FROM Attendance a WHERE a.memberID = :memberID AND a.timeOut IS NULL ORDER BY a.date DESC, a.timeIn DESC")
    Optional<Attendance> findLatestAttendanceWithNullTimeOut(@Param("memberID") Member member);*/


    List<Attendance> findByDate(LocalDate date);

}
