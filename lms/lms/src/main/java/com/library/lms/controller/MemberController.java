package com.library.lms.controller;


import com.library.lms.DTO.MemberDetailsDTO;
import com.library.lms.DTO.MemberRequest;
import com.library.lms.entity.Member;
import com.library.lms.entity.Staff;
import com.library.lms.entity.Students;
import com.library.lms.repository.MemberRepository;
import com.library.lms.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("api/members")
public class MemberController {


    @Autowired
    private final MemberService memberService;

    @Autowired
    private final MemberRepository memberRepository;

    public MemberController(MemberService memberService, MemberRepository memberRepository) {
        this.memberService = memberService;
        this.memberRepository = memberRepository;
    }


    @PostMapping
    public Member createMember(@RequestBody Member member){
        member = memberService.createMember(member);
        return member;
    }

    @PostMapping("/{memberId}/staff")
    public Staff createStaff(@PathVariable Integer memberId, @RequestBody Staff staff){
        return memberService.createStaff(memberId, staff);
    }

    @PostMapping("/{memberId}/student")
    public ResponseEntity<Students> createStudent(
            @PathVariable Integer memberId,
            @RequestBody Students student) {
        Students savedStudent = memberService.createStudent(memberId, student);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedStudent);
    }

    @GetMapping
    public List<MemberRequest> getAllMember(){
        return memberService.getAllMembers()
                .stream()
                .map(MemberRequest::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/{memberId}")
    public MemberDetailsDTO getMemberById(@PathVariable Integer memberId) {
        return memberService.getMemberDetails(memberId);
    }

    // Endpoint to get member details by firstName and lastName
    @GetMapping("/name")
    public ResponseEntity<Member> getMemberByName(@RequestParam String firstName, @RequestParam String lastName) {
        Optional<Member> member = memberRepository.findByFirstNameAndLastNameIgnoreCase(firstName, lastName);
        if (member.isPresent()) {
            return ResponseEntity.ok(member.get());
        } else {
            throw new RuntimeException("Member not found");
        }
    }
}
