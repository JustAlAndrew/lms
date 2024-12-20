package com.library.lms.DTO;


import com.library.lms.entity.Staff;
import lombok.Builder;
import lombok.Data;

@Data
public class StaffMemberDTO {

    //fields if member is staff
    private String position;
    private String shift;
    private String staffStatus;

    public StaffMemberDTO() {}

    public static StaffMemberDTO fromEntity(Staff staff){
        StaffMemberDTO dto = new StaffMemberDTO();

        dto.setStaffStatus(staff.getStaffStatus());
        dto.setShift(staff.getShift());
        dto.setPosition(staff.getPosition());
        return dto;
    }
}
