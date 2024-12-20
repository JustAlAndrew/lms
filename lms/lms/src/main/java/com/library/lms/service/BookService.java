package com.library.lms.service;

import com.library.lms.entity.Books;
import com.library.lms.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Books> getAllBooks() {
        return bookRepository.findAll();
    }

    public Books addBook(Books book) {
        return bookRepository.save(book);
    }

    public List<Books> searchBooksByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Books> searchBooksByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author);
    }

    public List<Books> searchBooksByCategory(String category) {
        return bookRepository.findByCategoryContainingIgnoreCase(category);
    }

    public List<String> getAllCategories() {
        return bookRepository.findDistinctCategories();
    }
}
