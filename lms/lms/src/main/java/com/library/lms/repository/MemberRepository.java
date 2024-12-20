package com.library.lms.repository;

import com.library.lms.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Component
@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
    @Query("SELECT m FROM Member m")
    List<Member> findAll();

    @Override
    Optional<Member> findById(Integer id);

    @Query("SELECT m FROM Member m JOIN m.student s WHERE s.schoolStudentID = :schoolStudentID")
    Member findBySchoolStudentID(@Param("schoolStudentID") String schoolStudentID);

    @Query("SELECT m FROM Member m JOIN m.staff s " +
            "WHERE m.lastName = :lastName AND m.firstName = :firstName AND s.position = :position")
    Member findByFullNameAndPosition(@Param("lastName") String lastName,
                                     @Param("firstName") String firstName,
                                     @Param("position") String position);

    @Query("SELECT m FROM Member m WHERE LOWER(m.firstName) = LOWER(:firstName) AND LOWER(m.lastName) = LOWER(:lastName)")
    Optional<Member> findByFirstNameAndLastNameIgnoreCase(@Param("firstName") String firstName, @Param("lastName") String lastName);
}
