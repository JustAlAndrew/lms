package com.library.lms.DTO;


import com.library.lms.entity.Students;
import lombok.Builder;
import lombok.Data;

@Data
public class StudentMemberDTO {

    //fields if member is student
    private int schoolStudentID;
    private String course;
    private int yearLevel;
    private String department;

    public StudentMemberDTO() {

    }


    public static StudentMemberDTO fromEntity(Students student){
        StudentMemberDTO dto = new StudentMemberDTO();

        dto.setSchoolStudentID(student.getSchoolStudentID());
        dto.setCourse(student.getCourse());
        dto.setYearLevel(student.getYearLevel());
        dto.setDepartment(student.getDepartment());
        return dto;
    }
}
