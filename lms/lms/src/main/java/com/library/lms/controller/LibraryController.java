package com.library.lms.controller;

import com.library.lms.DTO.BorrowRequest;
import com.library.lms.DTO.ReadRequest;
import com.library.lms.DTO.ReturnRequest;
import com.library.lms.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/library")
public class LibraryController {

    @Autowired
    private LibraryService libraryService;

    @PostMapping("/borrow")
    public ResponseEntity<String> borrowBook(@RequestBody BorrowRequest request) {
        libraryService.borrowBook(request.getMemberId(), request.getBookId());
        return ResponseEntity.ok("Book successfully borrowed.");
    }

    @PostMapping("/read")
    public ResponseEntity<String> readBook(@RequestBody ReadRequest request) {
        libraryService.readBook(request.getMemberId(), request.getBookId());
        return ResponseEntity.ok("Book successfully marked as being read.");
    }

    @PostMapping("/return")
    public ResponseEntity<String> returnBook(@RequestBody ReturnRequest request) {
        libraryService.returnBook(request.getMemberId(), request.getBookId());
        return ResponseEntity.ok("Book successfully returned.");
    }
}
