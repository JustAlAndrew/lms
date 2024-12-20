package com.library.lms.DTO;

import com.library.lms.entity.Member;
import lombok.Data;

@Data
public class MemberDetailsDTO {

    private Integer memberID;
    private String firstName;
    private String lastName;
    private String memberType;

    private StaffMemberDTO staffDetails;
    private StudentMemberDTO studentDetails;

    public static MemberDetailsDTO fromEntity(Member member){
        MemberDetailsDTO dto = new MemberDetailsDTO();

        dto.setMemberID(member.getMemberID());
        dto.setFirstName(member.getFirstName());
        dto.setLastName(member.getLastName());
        dto.setMemberType(member.getMemberType());
        return dto;
    }
}
