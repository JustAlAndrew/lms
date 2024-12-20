package com.library.lms.repository;

import com.library.lms.entity.Books;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Books, Integer> {

    List<Books> findByTitleContainingIgnoreCase(String title);
    List<Books> findByAuthorContainingIgnoreCase(String author);
    List<Books> findByCategoryContainingIgnoreCase(String category);

    @Query("SELECT DISTINCT b.category FROM Books b")
    List<String> findDistinctCategories();
}
