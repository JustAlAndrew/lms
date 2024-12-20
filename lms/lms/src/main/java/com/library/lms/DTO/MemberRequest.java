package com.library.lms.DTO;

import com.library.lms.entity.Member;
import lombok.Data;


@Data
public class MemberRequest {

    private Integer memberID;
    private String firstName;
    private String lastname;

    public static MemberRequest fromEntity(Member member){
        MemberRequest dto = new MemberRequest();
        dto.setMemberID(member.getMemberID());
        dto.setFirstName(member.getFirstName());
        dto.setLastname(member.getLastName());
        return dto;
    }


    private Member member;
    private StudentMemberDTO studentRequest;
    private StaffMemberDTO staffRequest;
}
