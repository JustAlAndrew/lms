package com.library.lms.repository;


import com.library.lms.entity.Member;
import com.library.lms.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Component
@Repository
public interface StaffRepository extends JpaRepository<Staff, Integer> {

    @Query("SELECT f FROM Staff f WHERE f.memberI.memberID = :memberID")
    Optional<Member> findMember(@Param("memberID")Integer memberID);

}
