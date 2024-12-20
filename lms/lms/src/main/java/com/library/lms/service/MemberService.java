package com.library.lms.service;

import com.library.lms.DTO.MemberDetailsDTO;
import com.library.lms.DTO.StaffMemberDTO;
import com.library.lms.DTO.StudentMemberDTO;
import com.library.lms.entity.Member;
import com.library.lms.entity.Staff;
import com.library.lms.entity.Students;
import com.library.lms.repository.MemberRepository;
import com.library.lms.repository.StaffRepository;
import com.library.lms.repository.StudentRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Component
@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StaffRepository staffRepository;


    public List<Member> getAllMembers(){
        return memberRepository.findAll();
    }

    public Member getByMemberId(Integer id){
        return memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found"));
    }

    public Member createMember(Member member){
        return memberRepository.save(member);
    }

    public Staff createStaff(Integer memberId, Staff staff){
        Member member = getByMemberId(memberId);
        staff.setMemberI(member);
        return staffRepository.save(staff);
    }

    public MemberDetailsDTO getMemberDetails(Integer memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        MemberDetailsDTO detailsDTO = MemberDetailsDTO.fromEntity(member);

        // Fetch staff or student details based on memberType
        if ("Staff".equalsIgnoreCase(member.getMemberType())) {
            Staff staff = staffRepository.findById(memberId)
                    .orElseThrow(() -> new RuntimeException("Staff details not found"));
            detailsDTO.setStaffDetails(StaffMemberDTO.fromEntity(staff));
        } else if ("Student".equalsIgnoreCase(member.getMemberType())) {
            Students student = studentRepository.findByMemberId(memberId)
                    .orElseThrow(() -> new RuntimeException("Student details not found"));
            detailsDTO.setStudentDetails(StudentMemberDTO.fromEntity(student));
        }

        return detailsDTO;
    }


    public Students createStudent(Integer memberId, Students student) {
        // Validate that the primary key is unique
        if (studentRepository.existsById(student.getSchoolStudentID())) {
            throw new IllegalArgumentException("Student ID already exists.");
        }

        Member member = getByMemberId(memberId);
        student.setMemberI(member);

        // Save student with custom primary key
        return studentRepository.save(student);
    }


}
