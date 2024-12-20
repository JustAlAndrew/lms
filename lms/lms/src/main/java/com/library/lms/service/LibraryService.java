package com.library.lms.service;

import com.library.lms.entity.Books;
import com.library.lms.entity.Borrow;
import com.library.lms.entity.Logs;
import com.library.lms.entity.Member;
import com.library.lms.repository.BookRepository;
import com.library.lms.repository.BorrowRepository;
import com.library.lms.repository.LogsRepository;
import com.library.lms.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Optional;

@Service
public class LibraryService {

    @Autowired
    private BorrowRepository borrowRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private LogsRepository logsRepository;

    @Autowired
    private MemberRepository memberRepository;

    // Enum for action types
    public enum ActionType {
        BORROW, RETURN, READ
    }

    // Borrow a book
    public void borrowBook(int memberId, int bookId) {
        // Fetch the book and ensure it's available
        Books book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        if (!book.isAvailable()) {
            throw new RuntimeException("Book is not available for borrowing.");
        }

        // Update the book's availability
        book.setAvailable(false);
        bookRepository.save(book);

        // Create a new borrow record
        Borrow borrow = new Borrow();
        borrow.setBorrowDate(new Date(System.currentTimeMillis())); // Current date
        borrow.setDueDate(new Date(System.currentTimeMillis() + (14L * 24 * 60 * 60 * 1000))); // Add 14 days
        borrow.setBooks(book);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        borrow.setMemberI(member);

        borrowRepository.save(borrow);

        // Log the action
        logAction(member, book, ActionType.BORROW);
    }

    // Return a book
    public void returnBook(int memberId, int bookId) {
        // Fetch the book and ensure it's currently borrowed
        Books book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (book.isAvailable()) {
            throw new RuntimeException("Book is already available.");
        }

        // Update the book's availability
        book.setAvailable(true);
        bookRepository.save(book);

        // Log the action
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        logAction(member, book, ActionType.RETURN);
    }

    // Read a book
    public void readBook(int memberId, int bookId) {
        // Fetch the book and ensure it's available
        Books book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (!book.isAvailable()) {
            throw new RuntimeException("Book is not available for reading.");
        }

        // Update the book's availability
        book.setAvailable(false);
        bookRepository.save(book);

        // Log the action
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        logAction(member, book, ActionType.READ);
    }

    // Helper method to log actions
    private void logAction(Member member, Books book, ActionType actionType) {
        Logs log = new Logs();
        log.setActionType(actionType.name());
        log.setActionDate(new Date(System.currentTimeMillis())); // Current date
        log.setBookI(book);
        log.setMemberI(member);

        logsRepository.save(log);
    }
}
