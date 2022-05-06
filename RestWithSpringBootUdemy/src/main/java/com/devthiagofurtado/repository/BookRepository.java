package com.devthiagofurtado.repository;

import com.devthiagofurtado.data.model.Book;
import com.devthiagofurtado.data.model.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b WHERE b.author LIKE CONCAT('%',:author,'%')")
    Page<Book> findAllByLikeAuthor(@Param("author") String author, Pageable pageable);

}
