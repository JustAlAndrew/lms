package com.library.lms.controller;

import com.library.lms.entity.Books;
import com.library.lms.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService booksService;

    @GetMapping
    public ResponseEntity<List<Books>> getAllBooks() {
        return ResponseEntity.ok(booksService.getAllBooks());
    }

    @PostMapping
    public ResponseEntity<Books> addBook(@RequestBody Books book) {
        return ResponseEntity.ok(booksService.addBook(book));
    }

    @GetMapping("/search/title")
    public ResponseEntity<List<Books>> searchBooksByTitle(@RequestParam String title) {
        return ResponseEntity.ok(booksService.searchBooksByTitle(title));
    }

    @GetMapping("/search/author")
    public ResponseEntity<List<Books>> searchBooksByAuthor(@RequestParam String author) {
        return ResponseEntity.ok(booksService.searchBooksByAuthor(author));
    }

    @GetMapping("/search/category")
    public ResponseEntity<List<Books>> searchBooksByCategory(@RequestParam String category) {
        return ResponseEntity.ok(booksService.searchBooksByCategory(category));
    }

    @GetMapping("/categories")
    public ResponseEntity<List<String>> getAllCategories() {
        return ResponseEntity.ok(booksService.getAllCategories());
    }
}
