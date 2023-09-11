package com.demo.books.management;

import com.demo.books.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {
    List<Book> findAllByNameContaining(String sub);
}
