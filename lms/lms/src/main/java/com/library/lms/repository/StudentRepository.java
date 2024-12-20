package com.library.lms.repository;


import com.library.lms.entity.Member;
import com.library.lms.entity.Students;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Students, Integer> {
    // Define a method to find a student by member ID
    @Query("SELECT s FROM Students s WHERE s.memberI.memberID = :memberID")
    Optional<Students> findByMemberId(@Param("memberID") Integer memberID);

    Optional<Students> findBySchoolStudentID(Integer schoolStudentID);

    boolean existsById(Integer schoolStudentID); // Method to check if ID exists


}
